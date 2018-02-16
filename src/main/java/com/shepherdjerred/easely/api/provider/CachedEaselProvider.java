package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.model.*;
import com.shepherdjerred.easely.api.refresher.scraper.*;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class CachedEaselProvider implements Provider {

    public CachedEaselProvider() {

    }

    private Map<String, String> login(User user) {
        Map<String, String> cookies;

        LoginScraper loginScraper = new LoginScraper();
        loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
        cookies = loginScraper.getCookies();

        return cookies;
    }

    @Override
    public Collection<Course> getCoursesForUser(User user) {
        Collection<CourseCore> userCourses;
        Collection<Course> courses = new ArrayList<>();

        Map<String, String> cookies = login(user);
        userCourses = new UserCourseScraper().getCourses(cookies);

        for (CourseCore courseCore : userCourses) {
            Course course;

            CourseDetails courseDetails;

            CourseDetailsScraper courseDetailsScraper = new CourseDetailsScraper();
            courseDetails = courseDetailsScraper.loadCourseDetails(cookies, courseCore.getId());

            CourseGrade courseGrade;

            CourseGradeScraper courseGradeScraper = new CourseGradeScraper();

            String easelUserId;


            UserIdScraper userIdScraper = new UserIdScraper();
            easelUserId = userIdScraper.getUserId(cookies);


            courseGrade = courseGradeScraper.loadCourseGrades(cookies, courseCore.getId(), easelUserId);


            course = Course.fromSubObjects(courseCore, courseDetails, courseGrade);
            courses.add(course);
        }

        return courses;
    }

    @Override
    public Collection<Assignment> getAssignmentsForUser(User user) {
        Collection<Assignment> assignments = new ArrayList<>();
        getCoursesForUser(user).forEach(course -> getAssignments(user, course).forEach( assignments::add));
        return assignments;
    }

    public Collection<Assignment> getAssignments(User user, Course course) {
        Collection<Assignment> assignments = new ArrayList<>();
        Collection<AssignmentCore> courseAssignments;


        Map<String, String> cookies = login(user);

        courseAssignments = new CourseAssignmentScraper(cookies).getAssignmentsForCourse(course);

        for (AssignmentCore assignmentCore : courseAssignments) {
            Assignment assignment;
            AssignmentDetails assignmentDetails;

            AssignmentDetailsScraper assignmentDetailsScraper = new AssignmentDetailsScraper();
            assignmentDetails = assignmentDetailsScraper.loadAssignmentDetails(cookies, assignmentCore.getId());

            if (assignmentCore.getType() == Assignment.Type.NOTES) {
                assignment = Assignment.fromSubObjects(assignmentCore, assignmentDetails);
            } else {
                AssignmentGrade assignmentGrade;
                AssignmentGradeScraper assignmentGradeScraper = new AssignmentGradeScraper();
                assignmentGrade = assignmentGradeScraper.loadAssignmentGrade(cookies, assignmentCore.getId());

                assignment = GradedAssignment.fromSubObjects(assignmentCore, assignmentDetails, assignmentGrade);
            }

            assignments.add(assignment);
        }

        return assignments;
    }
}

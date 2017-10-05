package com.shepherdjerred.easely.api.provider.easel;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.GradedAssignment;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.*;
import com.shepherdjerred.easely.api.provider.easel.scraper.objects.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LiveEaselProvider implements Provider {

    private Map<String, String> login(User user) {
        Map<String, String> cookies;

        LoginScraper loginScraper = new LoginScraper();
        loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
        cookies = loginScraper.getCookies();

        return cookies;
    }

    @Override
    public Collection<Course> getCourses(User user) {
        Map<String, String> cookies = login(user);

        Collection<CourseCore> userCourses;
        Collection<Course> courses = new ArrayList<>();

        // Find out what courses a user is in
        // We should only load the CourseCore here
        userCourses = new UserCourseScraper().getCourses(cookies);

        // Load details for the courses
        // Here we get the actual Course object
        for (CourseCore courseCore : userCourses) {
            Course course;
            CourseDetailsScraper courseDetailsScraper = new CourseDetailsScraper();
            CourseDetails courseDetails = courseDetailsScraper.loadCourseDetails(cookies, courseCore.getId());
            course = Course.fromSubObjects(courseCore, courseDetails);
            courses.add(course);
        }

        return courses;
    }

    @Override
    public Collection<Assignment> getAssignments(User user) {
        Collection<Assignment> assignments = new ArrayList<>();
        getCourses(user).forEach(course -> {
            getAssignments(user, course).forEach(assignment -> {
                assignments.add(assignment);
            });
        });
        return assignments;
    }

    @Override
    public Collection<Assignment> getAssignments(User user, Course course) {
        Collection<Assignment> assignments = new ArrayList<>();
        Collection<AssignmentCore> courseAssignments;

        // Find Assignments for Course
        Map<String, String> cookies = login(user);

        courseAssignments = new CourseAssignmentScraper(cookies).getAssignmentsForCourse(course);
        
        // Load details and grade for assignment
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

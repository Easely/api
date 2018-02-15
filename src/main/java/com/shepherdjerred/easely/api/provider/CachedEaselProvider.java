package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.model.*;
import com.shepherdjerred.easely.api.refresher.scraper.*;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
public class CachedEaselProvider implements Provider {

    private RedissonClient redisson;

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

        RBucket<Collection<CourseCore>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");

        // Find out what courses a user is in
        // We should only load the CourseCore here
        if (userCoursesBucket.isExists()) {
            userCourses = userCoursesBucket.get();
        } else {
            Map<String, String> cookies = login(user);
            userCourses = new UserCourseScraper().getCourses(cookies);
            userCoursesBucket.set(userCourses);
            userCoursesBucket.expire(30, TimeUnit.DAYS);
        }

        // TODO Cache the CourseCore?
        // Why would we do this?
        // CourseCore is loaded when the user requests their courses
        // CourseCore is rarely updated, but we have nothing to lose by updating the cache

        // Load details for the courses
        // Here we get the actual Course model
        for (CourseCore courseCore : userCourses) {
            Course course;

            RBucket<CourseDetails> courseDetailsBucket = redisson.getBucket("course:details:" + courseCore.getId());
            CourseDetails courseDetails;
            if (courseDetailsBucket.isExists()) {
                courseDetails = courseDetailsBucket.get();
            } else {
                Map<String, String> cookies = login(user);
                CourseDetailsScraper courseDetailsScraper = new CourseDetailsScraper();
                courseDetails = courseDetailsScraper.loadCourseDetails(cookies, courseCore.getId());
                courseDetailsBucket.set(courseDetails);
                courseDetailsBucket.expire(7, TimeUnit.DAYS);
            }

            RBucket<CourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
            CourseGrade courseGrade;
            if (courseGradeBucket.isExists()) {
                courseGrade = courseGradeBucket.get();
            } else {
                Map<String, String> cookies = login(user);
                CourseGradeScraper courseGradeScraper = new CourseGradeScraper();

                RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
                String easelUserId;

                if (easelUserIdBucket.isExists()) {
                    easelUserId = easelUserIdBucket.get();
                } else {
                    UserIdScraper userIdScraper = new UserIdScraper();
                    easelUserId = userIdScraper.getUserId(cookies);
                    easelUserIdBucket.set(easelUserId);
                }

                courseGrade = courseGradeScraper.loadCourseGrades(cookies, courseCore.getId(), easelUserId);
                courseGradeBucket.set(courseGrade);
                courseGradeBucket.expire(1, TimeUnit.DAYS);
            }

            course = Course.fromSubObjects(courseCore, courseDetails, courseGrade);
            courses.add(course);
        }

        return courses;
    }

    @Override
    public Collection<Assignment> getAssignmentsForUser(User user) {
        Collection<Assignment> assignments = new ArrayList<>();
        getCoursesForUser(user).forEach(course -> {
            getAssignments(user, course).forEach(assignment -> {
                assignments.add(assignment);
            });
        });
        return assignments;
    }

    public Collection<Assignment> getAssignments(User user, Course course) {
        Collection<Assignment> assignments = new ArrayList<>();
        Collection<AssignmentCore> courseAssignments;
        RBucket<Collection<AssignmentCore>> courseAssignmentsBucket = redisson.getBucket("course:" + course.getId() + ":assignments");

        // Find Assignments for Course
        if (courseAssignmentsBucket.isExists()) {
            courseAssignments = courseAssignmentsBucket.get();
        } else {
            Map<String, String> cookies = login(user);

            courseAssignments = new CourseAssignmentScraper(cookies).getAssignmentsForCourse(course);
            courseAssignmentsBucket.set(courseAssignments);
            courseAssignmentsBucket.expire(1, TimeUnit.DAYS);
        }

        // TODO Cache AssignmentCore?

        // Load details and grade for assignment
        for (AssignmentCore assignmentCore : courseAssignments) {
            RBucket<AssignmentDetails> assignmentDetailsBucket = redisson.getBucket("assignment:details:" + assignmentCore.getId());
            Assignment assignment;
            AssignmentDetails assignmentDetails;
            if (assignmentDetailsBucket.isExists()) {
                assignmentDetails = assignmentDetailsBucket.get();
            } else {
                Map<String, String> cookies = login(user);
                AssignmentDetailsScraper assignmentDetailsScraper = new AssignmentDetailsScraper();
                assignmentDetails = assignmentDetailsScraper.loadAssignmentDetails(cookies, assignmentCore.getId());
                assignmentDetailsBucket.set(assignmentDetails);
                // If the due date hasn't past, let's update the assignment daily
                if (assignmentCore.getDate().isAfter(LocalDate.now())) {
                    assignmentDetailsBucket.expire(1, TimeUnit.DAYS);
                }
            }

            if (assignmentCore.getType() == Assignment.Type.NOTES) {
                assignment = Assignment.fromSubObjects(assignmentCore, assignmentDetails);
            } else {
                RBucket<AssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
                AssignmentGrade assignmentGrade;
                if (assignmentGradeBucket.isExists()) {
                    assignmentGrade = assignmentGradeBucket.get();
                } else {
                    Map<String, String> cookies = login(user);
                    AssignmentGradeScraper assignmentGradeScraper = new AssignmentGradeScraper();
                    assignmentGrade = assignmentGradeScraper.loadAssignmentGrade(cookies, assignmentCore.getId());
                    assignmentGradeBucket.set(assignmentGrade);
                    // If the due date hasn't past, let's not update it until it's due
                    if (assignmentCore.getDate().isAfter(LocalDate.now())) {
                        assignmentGradeBucket.expireAt(Date.valueOf(assignmentCore.getDate()));
                    } else {
                        // If the assigment isn't graded, let's refresh it in a day
                        if (assignmentGrade.isGraded()) {
                            assignmentGradeBucket.expire(1, TimeUnit.DAYS);
                        }
                    }

                }
                assignment = GradedAssignment.fromSubObjects(assignmentCore, assignmentDetails, assignmentGrade);
            }

            assignments.add(assignment);
        }

        return assignments;
    }
}

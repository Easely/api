package com.shepherdjerred.easely.api.easel.cache;

// TODO eliminate duplication of bucket definitions

import com.shepherdjerred.easely.api.CacheException;
import com.shepherdjerred.easely.api.easel.scraper.cache.ScraperCache;
import com.shepherdjerred.easely.api.easel.scraper.model.*;
import com.shepherdjerred.easely.api.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class RedisEaselCache implements EaselCache {

    private ScraperCache scraperCache;

    // TODO do this better
    @Override
    public boolean hasUserCourses(User user) {
        try {
            getUserCourses(user);
        } catch (CacheException e) {
            return false;
        }
        return true;
    }

    // TODO do this better
    @Override
    public boolean hasUserAssignments(User user) {
        try {
            getUserAssignments(user);
        } catch (CacheException e) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<Course> getUserCourses(User user) throws CacheException {
        Collection<Course> courses = new ArrayList<>();

        Collection<CourseCore> userCourseCores = scraperCache.getUserCourseCores(user);
        for (CourseCore courseCore : userCourseCores) {
            Course course = loadFullUserCourse(user, courseCore);
            courses.add(course);
        }

        return courses;
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) throws CacheException {
        Collection<Assignment> assignments = new ArrayList<>();

        Collection<CourseCore> userCourseCores = scraperCache.getUserCourseCores(user);
        for (CourseCore courseCore : userCourseCores) {
            Collection<AssignmentCore> assignmentCores = scraperCache.getCourseAssignmentCores(courseCore);
            for (AssignmentCore assignmentCore : assignmentCores) {
                Assignment assignment = loadFullUserAssignment(user, assignmentCore);
                assignments.add(assignment);
            }
        }

        return assignments;
    }

    private CourseDetails loadCourseDetails(CourseCore courseCore) throws CacheException {
        return scraperCache.getCourseDetails(courseCore);
    }

    private UserCourseGrade loadUserCourseGrade(User user, CourseCore courseCore) throws CacheException {
        return scraperCache.getUserCourseGrade(user, courseCore);
    }

    private Course loadFullUserCourse(User user, CourseCore courseCore) throws CacheException {
        CourseDetails courseDetails = loadCourseDetails(courseCore);
        UserCourseGrade courseGrade = loadUserCourseGrade(user, courseCore);
        return Course.fromSubObjects(courseCore, courseDetails, courseGrade);
    }

    private AssignmentDetails loadAssignmentDetails(AssignmentCore assignmentCore) throws CacheException {
        return scraperCache.getAssignmentDetails(assignmentCore);
    }

    private UserAssignmentGrade loadAssignmentGrade(User user, AssignmentCore assignmentCore) throws CacheException {
        return scraperCache.getUserAssignmentGrade(user, assignmentCore);
    }

    private Assignment loadFullUserAssignment(User user, AssignmentCore assignmentCore) throws CacheException {
        AssignmentDetails assignmentDetails = loadAssignmentDetails(assignmentCore);

        if (assignmentCore.getType() == Assignment.Type.NOTES) {
            return Assignment.fromSubObjects(assignmentCore, assignmentDetails);
        } else {
            UserAssignmentGrade assignmentGrade = loadAssignmentGrade(user, assignmentCore);
            return GradedAssignment.fromSubObjects(assignmentCore, assignmentDetails, assignmentGrade);
        }
    }
}

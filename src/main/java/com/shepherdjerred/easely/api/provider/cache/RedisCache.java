package com.shepherdjerred.easely.api.provider.cache;

import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.model.*;
import com.shepherdjerred.easely.api.provider.cache.updater.easel.model.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;

// TODO eliminate duplication of bucket definitions

@Log4j2
public class RedisCache extends RedisScraperCache implements Cache {

    public RedisCache(EaselyConfig easelyConfig) {
        super(easelyConfig);
    }

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

        Collection<CourseCore> userCourseCores = getUserCourseCores(user);
        for (CourseCore courseCore : userCourseCores) {
            Course course = loadFullUserCourse(user, courseCore);
            courses.add(course);
        }

        return courses;
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) throws CacheException {
        Collection<Assignment> assignments = new ArrayList<>();

        Collection<CourseCore> userCourseCores = getUserCourseCores(user);
        for (CourseCore courseCore : userCourseCores) {
            Collection<AssignmentCore> assignmentCores = getCourseAssignmentCores(courseCore);
            for (AssignmentCore assignmentCore : assignmentCores) {
                Assignment assignment = loadFullUserAssignment(user, assignmentCore);
                assignments.add(assignment);
            }
        }

        return assignments;
    }

    private CourseDetails loadCourseDetails(CourseCore courseCore) throws CacheException {
        return getCourseDetails(courseCore);
    }

    private CourseGrade loadUserCourseGrade(User user, CourseCore courseCore) throws CacheException {
        return getCourseGrade(user, courseCore);
    }

    private Course loadFullUserCourse(User user, CourseCore courseCore) throws CacheException {
        CourseDetails courseDetails = loadCourseDetails(courseCore);
        CourseGrade courseGrade = loadUserCourseGrade(user, courseCore);
        return Course.fromSubObjects(courseCore, courseDetails, courseGrade);
    }

    private AssignmentDetails loadAssignmentDetails(AssignmentCore assignmentCore) throws CacheException {
        return getAssignmentDetails(assignmentCore);
    }

    private AssignmentGrade loadAssignmentGrade(User user, AssignmentCore assignmentCore) throws CacheException {
        return getAssignmentGrade(user, assignmentCore);
    }

    private Assignment loadFullUserAssignment(User user, AssignmentCore assignmentCore) throws CacheException {
        AssignmentDetails assignmentDetails = loadAssignmentDetails(assignmentCore);

        if (assignmentCore.getType() == Assignment.Type.NOTES) {
            return Assignment.fromSubObjects(assignmentCore, assignmentDetails);
        } else {
            AssignmentGrade assignmentGrade = loadAssignmentGrade(user, assignmentCore);
            return GradedAssignment.fromSubObjects(assignmentCore, assignmentDetails, assignmentGrade);
        }
    }
}

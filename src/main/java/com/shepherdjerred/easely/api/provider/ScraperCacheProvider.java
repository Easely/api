package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.refresher.scraper.cache.ScraperCache;
import com.shepherdjerred.easely.api.model.*;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;

// TODO don't create the full objects here. this class should only load from a cache

@Log4j2
public class ScraperCacheProvider implements Provider {

    private ScraperCache scraperCache;

    public ScraperCacheProvider(ScraperCache scraperCache) {
        this.scraperCache = scraperCache;
    }

    @Override
    public Collection<Course> getUserCourses(User user) {
        Collection<Course> courses = new ArrayList<>();
        Collection<CourseCore> userCourseCores = scraperCache.getUserCourseCores(user);
        userCourseCores.forEach(courseCore -> {
            CourseDetails courseDetails = scraperCache.getCourseDetails(courseCore);
            CourseGrade courseGrade = scraperCache.getCourseGrade(user, courseCore);
            Course course = Course.fromSubObjects(courseCore, courseDetails, courseGrade);
            courses.add(course);
        });
        return courses;
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) {
        Collection<Assignment> assignments = new ArrayList<>();
        Collection<CourseCore> userCourseCores = scraperCache.getUserCourseCores(user);
        userCourseCores.forEach(courseCore -> {
            Collection<AssignmentCore> assignmentCores = scraperCache.getCourseAssignmentCores(courseCore);
            assignmentCores.forEach(assignmentCore -> {
                AssignmentDetails assignmentDetails = scraperCache.getAssignmentDetails(assignmentCore);
                if (assignmentCore.getType() == Assignment.Type.NOTES) {
                    Assignment assignment = Assignment.fromSubObjects(assignmentCore, assignmentDetails);
                    assignments.add(assignment);
                } else {
                    AssignmentGrade assignmentGrade = scraperCache.getAssignmentGrade(user, assignmentCore);
                    GradedAssignment gradedAssignment = GradedAssignment.fromSubObjects(assignmentCore, assignmentDetails, assignmentGrade);
                    assignments.add(gradedAssignment);
                }
            });
        });
        return assignments;
    }
}

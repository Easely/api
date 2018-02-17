package com.shepherdjerred.easely.api.provider.loader;

import com.shepherdjerred.easely.api.provider.scraper.cache.ScraperCache;
import com.shepherdjerred.easely.api.model.*;
import com.shepherdjerred.easely.api.provider.scraper.objects.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class ScraperCacheLoader implements Loader {

    private ScraperCache scraperCache;

    // TODO we need to detect if we're missing objects here
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

    // TODO we need to detect if we're missing objects here
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

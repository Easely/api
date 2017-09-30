package com.shepherdjerred.easely.api.provider.easel;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.AssignmentScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.CourseScraper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EaselProvider implements Provider {

    @Override
    public Collection<Course> getCourses() {
        return new CourseScraper().getCourses();
    }

    @Override
    public Map<Assignment, Course> getAssignments() {
        Map<Assignment, Course> assignmentCourseMap = new HashMap<>();
        getCourses().forEach(course -> {
            getAssignments(course).forEach(assignment -> {
               assignmentCourseMap.put(assignment, course);
            });
        });
        return assignmentCourseMap;
    }

    @Override
    public Collection<Assignment> getAssignments(Course course) {
        return new AssignmentScraper().getAssignments(course);
    }
}

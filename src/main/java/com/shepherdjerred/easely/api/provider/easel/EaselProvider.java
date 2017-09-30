package com.shepherdjerred.easely.api.provider.easel;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.AssignmentScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.CourseScraper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EaselProvider implements Provider {

    @Override
    public Collection<Course> getCourses(User user) {
        return new CourseScraper().getCourses(user);
    }

    @Override
    public Map<Assignment, Course> getAssignments(User user) {
        Map<Assignment, Course> assignmentCourseMap = new HashMap<>();
        getCourses(user).forEach(course -> {
            getAssignments(user, course).forEach(assignment -> {
               assignmentCourseMap.put(assignment, course);
            });
        });
        return assignmentCourseMap;
    }

    @Override
    public Collection<Assignment> getAssignments(User user, Course course) {
        return new AssignmentScraper().getAssignments(user, course);
    }
}

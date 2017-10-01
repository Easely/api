package com.shepherdjerred.easely.api.provider.easel;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.AssignmentScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.CourseScraper;

import java.util.ArrayList;
import java.util.Collection;

public class EaselProvider implements Provider {

    @Override
    public Collection<Course> getCourses(User user) {
        return new CourseScraper().getCourses(user);
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
        return new AssignmentScraper().getAssignments(user, course);
    }
}

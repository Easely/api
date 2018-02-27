package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class ScraperLoader implements Loader {
    @Override
    public Collection<Course> getUserCourses(User user) {
        return null;
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) {
        return null;
    }
}

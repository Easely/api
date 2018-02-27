package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class QueuedLoader implements Loader {
    private Loader loader;

    @Override
    public Collection<Course> getUserCourses(User user) {
        // TODO
        // check if we are already loading the data, otherwise call the loader
        return loader.getUserCourses(user);
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) {
        // TODO
        // check if we are already loading the data, otherwise call the loader
        return loader.getUserAssignments(user);
    }
}

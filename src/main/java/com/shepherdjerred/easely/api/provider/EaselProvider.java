package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;

import java.util.Collection;

public class EaselProvider implements Provider {
    @Override
    public Collection<Course> getCourses() {
        return null;
    }

    @Override
    public Collection<Assignment> getAssignments() {
        return null;
    }

    @Override
    public Collection<Assignment> getAssignments(Course course) {
        return null;
    }
}

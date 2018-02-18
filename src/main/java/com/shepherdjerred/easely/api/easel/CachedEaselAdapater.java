package com.shepherdjerred.easely.api.easel;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class CachedEaselAdapater implements EaselAdapter {
    @Override
    public AdapterContent<Collection<Course>> getUserCourses(User user) {
        return null;
    }

    @Override
    public AdapterContent<Collection<Assignment>> getUserAssignments(User user) {
        return null;
    }
}

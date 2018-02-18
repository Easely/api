package com.shepherdjerred.easely.api.easel;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public interface EaselAdapter {
    AdapterContent<Collection<Course>> getUserCourses(User user);

    AdapterContent<Collection<Assignment>> getUserAssignments(User user);
}

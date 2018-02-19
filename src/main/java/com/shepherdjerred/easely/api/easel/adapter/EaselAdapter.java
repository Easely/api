package com.shepherdjerred.easely.api.easel.adapter;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public interface EaselAdapter {
    LoadingContent<Collection<Course>> getUserCourses(User user);

    LoadingContent<Collection<Assignment>> getUserAssignments(User user);
}

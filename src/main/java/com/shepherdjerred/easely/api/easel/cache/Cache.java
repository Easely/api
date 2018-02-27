package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;
import java.util.concurrent.Future;

public interface Cache {
    Future<Collection<Course>> getUserCourses(User user);

    Future<Collection<Assignment>> getUserAssignments(User user);
}

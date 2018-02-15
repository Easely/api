package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;

import java.util.Collection;

public interface Provider {
    Collection<Course> getCoursesForUser(User user);
    Collection<Assignment> getAssignmentsForUser(User user);
}

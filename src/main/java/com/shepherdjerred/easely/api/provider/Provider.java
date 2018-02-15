package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public interface Provider {
    Collection<Course> getCoursesForUser(User user);
    Collection<Assignment> getAssignmentsForUser(User user);
}

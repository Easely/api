package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;

import java.util.Collection;

public interface Provider {
    Collection<Course> getCourses(User user);
    Collection<Assignment> getAssignments(User user);
    Collection<Assignment> getAssignments(User user, Course course);
}

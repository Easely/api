package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;

import java.util.Collection;

public interface Provider {
    Collection<Course> getCourses();
    Collection<Assignment> getAssignments();
    Collection<Assignment> getAssignments(Course course);
}

package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;

import java.util.Collection;
import java.util.Map;

public interface Provider {
    Collection<Course> getCourses();
    Map<Assignment, Course> getAssignments();
    Collection<Assignment> getAssignments(Course course);
}

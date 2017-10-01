package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ToString
public class MockProvider implements Provider {

    private Collection<Course> courses;
    private Map<Assignment, Course> assignmentCourseMap;

    public MockProvider() {
        courses = new ArrayList<>();
        assignmentCourseMap = new HashMap<>();

        addMockData();
    }

    private void addMockData() {
        // TODO
    }

    @Override
    public Collection<Course> getCourses(User user) {
        return courses;
    }

    @Override
    public Map<Assignment, Course> getAssignments(User user) {
        return assignmentCourseMap;
    }

    @Override
    public Collection<Assignment> getAssignments(User user, Course course) {
        Collection<Assignment> assignmentsForCourse = new ArrayList<>();
        assignmentCourseMap.forEach((key, value) -> {
            if (value == course) {
                assignmentsForCourse.add(key);
            }
        });
        return assignmentsForCourse;
    }
}

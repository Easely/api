package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
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
        Course comp150 = new Course("1", "COMP 150", "Programming I");
        Course comp151 = new Course("2", "COMP 151", "Programming II");
        Course comp170 = new Course("3", "COMP 170", "Software Development");

        courses.add(comp150);
        courses.add(comp151);
        courses.add(comp170);

        Assignment intro = new Assignment("1", "Intro to Programming I", 100, 0, true);
        Assignment anotherIntro = new Assignment("2", "Intro to Programming II", 100, 50, true);
        Assignment yetAnotherIntro = new Assignment("3", "Intro to Software Development", 100, 0, false);

        assignmentCourseMap.put(intro, comp150);
        assignmentCourseMap.put(anotherIntro, comp151);
        assignmentCourseMap.put(yetAnotherIntro, comp170);
    }

    @Override
    public Collection<Course> getCourses() {
        return courses;
    }

    @Override
    public Collection<Assignment> getAssignments() {
        return assignmentCourseMap.keySet();
    }

    @Override
    public Collection<Assignment> getAssignments(Course course) {
        Collection<Assignment> assignmentsForCourse = new ArrayList<>();
        assignmentCourseMap.forEach((key, value) -> {
            if (value == course) {
                assignmentsForCourse.add(key);
            }
        });
        return assignmentsForCourse;
    }
}

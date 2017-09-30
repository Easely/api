package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import lombok.ToString;

import java.time.LocalDate;
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

        Assignment intro = new Assignment("1", "Intro to Programming I", LocalDate.now(), Assignment.Type.HOMEWORK, comp150);
        Assignment anotherIntro = new Assignment("2", "Intro to Programming II", LocalDate.now(), Assignment.Type.EXAM, comp151);
        Assignment yetAnotherIntro = new Assignment("3", "Intro to Software Development", LocalDate.now(), Assignment.Type.NOTES, comp170);

        assignmentCourseMap.put(intro, comp150);
        assignmentCourseMap.put(anotherIntro, comp151);
        assignmentCourseMap.put(yetAnotherIntro, comp170);
    }

    @Override
    public Collection<Course> getCourses() {
        return courses;
    }

    @Override
    public Map<Assignment, Course> getAssignments() {
        return assignmentCourseMap;
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

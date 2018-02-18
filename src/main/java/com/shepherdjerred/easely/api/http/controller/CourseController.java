package com.shepherdjerred.easely.api.http.controller;

import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.easel.adapter.EaselAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class CourseController {

    private EaselAdapter easelAdapter;

    public Collection<Course> getCoursesForUser(User user) {
        if (easelAdapter.getUserCourses(user).isLoaded()) {
            return easelAdapter.getUserCourses(user).getContent();
        } else {
            // TODO
            return null;
        }
    }

}

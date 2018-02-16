package com.shepherdjerred.easely.api.controller;

import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.provider.Provider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class CourseController {

    private Provider provider;

    public Collection<Course> getCoursesForUser(User user) {
        return provider.getUserCourses(user);
    }

}

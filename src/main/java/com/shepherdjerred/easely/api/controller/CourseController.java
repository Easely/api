package com.shepherdjerred.easely.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.provider.Provider;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

import static spark.Spark.get;

@Log4j2
public class CourseController implements Controller {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Provider provider;

    public CourseController(Provider provider) {
        this.provider = provider;
    }

    @Override
    public void setupRoutes() {
        get("/api/courses", (request, response) -> {
            response.type("application/json");

            Collection<Course> courses = provider.getCourses();
            return objectMapper.writeValueAsString(courses);
        });
    }
}

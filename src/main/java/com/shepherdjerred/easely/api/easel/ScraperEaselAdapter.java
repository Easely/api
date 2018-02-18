package com.shepherdjerred.easely.api.easel;

import com.shepherdjerred.easely.api.easel.scraper.Scraper;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class ScraperEaselAdapter implements EaselAdapter {

    private Scraper scraper;

    @Override
    public AdapterContent<Collection<Course>> getUserCourses(User user) {
        Collection<Course> courses = scraper.scrapeUserCourse(user);
        return new AdapterContent<>(true,
                courses,
                null,
                false,
                0,
                0);
    }

    @Override
    public AdapterContent<Collection<Assignment>> getUserAssignments(User user) {
        Collection<Assignment> assignments = scraper.scrapeUserAssignments(user);
        return new AdapterContent<>(true,
                assignments,
                null,
                false,
                0,
                0);
    }
}
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
public class LiveEaselAdapater implements EaselAdapter {

    private Scraper scraper;

    @Override
    public Collection<Course> getUserCourses(User user) {
        return scraper.scrapeUserCourse(user);
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) {
        return scraper.scrapeUserAssignments(user);
    }
}

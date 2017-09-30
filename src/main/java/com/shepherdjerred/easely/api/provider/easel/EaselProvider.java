package com.shepherdjerred.easely.api.provider.easel;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.CourseScraper;

import java.util.ArrayList;
import java.util.Collection;

public class EaselProvider implements Provider {

    public EaselProvider() {

    }

    @Override
    public Collection<Course> getCourses() {
        return new CourseScraper().getCourses();
    }

    @Override
    public Collection<Assignment> getAssignments() {
        return new ArrayList<>();
    }

    @Override
    public Collection<Assignment> getAssignments(Course course) {
        return new ArrayList<>();
    }
}

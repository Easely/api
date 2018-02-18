package com.shepherdjerred.easely.api.easel.scraper;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class CachedEaselScraper implements Scraper{
    @Override
    public Collection<Course> scrapeUserCourse(User user) {
        return null;
    }

    @Override
    public Collection<Assignment> scrapeUserAssignments(User user) {
        return null;
    }
}

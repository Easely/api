package com.shepherdjerred.easely.api.easel.scraper;

import com.shepherdjerred.easely.api.easel.scraper.cache.ScraperCache;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
public class CachedEaselScraper implements EaselScraper {

    private ScraperCache scraperCache;

    @Override
    public Collection<Course> scrapeUserCourse(User user) {
        return null;
    }

    @Override
    public Collection<Assignment> scrapeUserAssignments(User user) {
        return null;
    }
}

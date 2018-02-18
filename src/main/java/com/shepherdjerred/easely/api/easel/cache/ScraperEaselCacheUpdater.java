package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.easel.scraper.Scraper;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.easel.scraper.model.CourseCore;
import com.shepherdjerred.easely.api.easel.scraper.model.CourseDetails;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class ScraperEaselCacheUpdater implements DataCacheUpdater {

    private Scraper scraper;
    private CacheReader cacheReader;

    @Override
    public void updateUserCourses(User user) {
        Collection<Course> courses;
        Collection<CourseCore> courseCores = scraper.getUserCourseCores(user);
        for (CourseCore courseCore : courseCores) {
            CourseDetails courseDetails = scraper.getCourseDetails(courseCore);
            CourseGrade courseGrade = scraper.getCourseGrade(user, courseCore);
        }
    }

    @Override
    public void updateUserAssignments(User user) {

    }
}

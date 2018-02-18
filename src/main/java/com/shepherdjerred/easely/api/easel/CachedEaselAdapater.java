package com.shepherdjerred.easely.api.easel;

import com.shepherdjerred.easely.api.easel.cache.EaselCache;
import com.shepherdjerred.easely.api.easel.scraper.Scraper;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class CachedEaselAdapater implements EaselAdapter {

    private EaselCache easelCache;
    private Scraper scraper;

    @Override
    public AdapterContent<Collection<Course>> getUserCourses(User user) {
        return null;
    }

    @Override
    public AdapterContent<Collection<Assignment>> getUserAssignments(User user) {
        return null;
    }

    private void updateUserCourses(User user) {

    }

    private void updateUserAssignments(User user) {

    }
}

package com.shepherdjerred.easely.api.easel.adapter;

import com.shepherdjerred.easely.api.easel.cache.Cache;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class CacheAdapter implements EaselAdapter {

    private Cache cache;

    @Override
    public LoadingContent<Collection<Course>> getUserCourses(User user) {
        cache.getUserCourses(user, () -> {
            return null;
        });

        return null;
    }

    @Override
    public LoadingContent<Collection<Assignment>> getUserAssignments(User user) {
        cache.getUserAssignments(user, () -> {
            return null;
        });

        return null;
    }
}

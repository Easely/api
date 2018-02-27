package com.shepherdjerred.easely.api.easel.datasource;

import com.shepherdjerred.easely.api.easel.cache.Cache;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public class CacheDataSource implements EaselDataSource {

    private Cache cache;

    @Override
    public DataSourceContent<Collection<Course>> getUserCourses(User user) {
        cache.getUserCourses(user);

        return null;
    }

    @Override
    public DataSourceContent<Collection<Assignment>> getUserAssignments(User user) {
        cache.getUserAssignments(user);

        return null;
    }
}

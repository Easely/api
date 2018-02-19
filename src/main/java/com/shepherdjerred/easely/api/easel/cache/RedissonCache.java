package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.easel.adapter.LoadingContent;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import org.redisson.Redisson;

import java.util.Collection;
import java.util.concurrent.Callable;

public class RedissonCache implements Cache {

    private Redisson redisson;

    @Override
    public LoadingContent<Collection<Cache>> getUserCourses(User user, Callable<Collection<Course>> callable) {
        return null;
    }

    @Override
    public LoadingContent<Collection<Assignment>> getUserAssignments(User user, Callable<Collection<Assignment>> callable) {
        return null;
    }
}

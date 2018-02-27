package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import org.redisson.Redisson;

import java.util.Collection;
import java.util.concurrent.Future;

public class RedissonCache implements Cache {

    private Redisson redisson;
    private Loader loader;

    @Override
    public Future<Collection<Course>> getUserCourses(User user) {
        if (hasUserCourses(user)) {
            // TODO
            return null;
        } else {

        }
    }

    @Override
    public Future<Collection<Assignment>> getUserAssignments(User user) {
        if (hasUserAssignments(user)) {
            // TODO
            return null;
        } else {

        }
    }

    private boolean hasUserCourses(User user) {
        return true;
    }

    private boolean hasUserAssignments(User user) {
        return true;
    }

    private Collection<Course> getUserCoursesFromCache(User user) {
        return null;
    }

    private Collection<Assignment> getUserAssignmentsFromCache(User user) {
        return null;
    }
}

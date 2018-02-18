package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.CacheException;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public interface EaselCache {
    boolean hasUserCourses(User user);

    boolean hasUserAssignments(User user);

    Collection<Course> getUserCourses(User user) throws CacheException;

    Collection<Assignment> getUserAssignments(User user) throws CacheException;
}

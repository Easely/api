package com.shepherdjerred.easely.api.provider.cache;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;

public interface Cache {
    boolean hasUserCourses(User user);

    boolean hasUserAssignments(User user);

    Collection<Course> getUserCourses(User user) throws CacheException;

    Collection<Assignment> getUserAssignments(User user) throws CacheException;
}

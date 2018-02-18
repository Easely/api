package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.CacheException;
import com.shepherdjerred.easely.api.easel.EaselAdapter;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class EaselCacheEaselAdapter implements EaselAdapter {

    private CacheReader cacheReader;
    private DataCacheUpdater dataCacheUpdater;

    @Override
    public Collection<Course> getUserCourses(User user) {

        try {
            if (cacheReader.hasUserCourses(user)) {
                log.debug("Courses for " + user.getEmail() + " found in cacheReader; loading");
                return cacheReader.getUserCourses(user);
            } else {
                // TODO do this in some sort of queue
                log.debug("Courses for " + user.getEmail() + " not found in cacheReader; running update");
                updateUserCourses(user);
                return cacheReader.getUserCourses(user);
            }
        } catch (CacheException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) {

        try {
            if (cacheReader.hasUserAssignments(user)) {
                log.debug("Assignments for " + user.getEmail() + " found in cacheReader; loading");
                return cacheReader.getUserAssignments(user);
            } else {
                // TODO do this in some sort of queue
                log.debug("Assignments for " + user.getEmail() + " not found in cacheReader; running update");
                updateUserAssignments(user);
                return cacheReader.getUserAssignments(user);
            }
        } catch (CacheException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateUserCourses(User user) {
        dataCacheUpdater.updateUserCourses(user);
    }

    @Override
    public void updateUserAssignments(User user) {
        dataCacheUpdater.updateUserAssignments(user);
    }

}

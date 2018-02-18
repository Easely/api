package com.shepherdjerred.easely.api.provider.cache;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.provider.Provider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class CacheProvider implements Provider {

    private Cache cache;

    @Override
    public Collection<Course> getUserCourses(User user) {

        try {
            if (cache.hasUserCourses(user)) {
                log.debug("Courses for " + user.getEmail() + " found in cache; loading");
                return cache.getUserCourses(user);
            } else {
                // TODO do this in some sort of queue
                log.debug("Courses for " + user.getEmail() + " not found in cache; running update");
                updateUserCourses();
                return cache.getUserCourses(user);
            }
        } catch (CacheException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<Assignment> getUserAssignments(User user) {

        try {
            if (cache.hasUserAssignments(user)) {
                log.debug("Assignments for " + user.getEmail() + " found in cache; loading");
                return cache.getUserAssignments(user);
            } else {
                // TODO do this in some sort of queue
                log.debug("Assignments for " + user.getEmail() + " not found in cache; running update");
                updateUserAssignments();
                return cache.getUserAssignments(user);
            }
        } catch (CacheException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateUserCourses() {

    }

    @Override
    public void updateUserAssignments() {

    }

    @Override
    public void updateCourse() {

    }

    @Override
    public void updateAssignment() {

    }

}

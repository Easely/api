package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.model.User;

public interface DataCacheUpdater {
    void updateUserCourses(User user);

    void updateUserAssignments(User user);
}

package com.shepherdjerred.easely.api.provider.refresher;

import com.shepherdjerred.easely.api.model.User;

public interface Refresher {
    void refreshUserCourses(User user);

    void refreshUserAssignments(User user);
}

package com.shepherdjerred.easely.api.http.controller;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.easel.adapter.EaselAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class AssignmentController {

    private EaselAdapter easelAdapter;

    public Collection<Assignment> getAssignmentsForUser(User user) {
        if (easelAdapter.getUserAssignments(user).isLoaded()) {
            return easelAdapter.getUserAssignments(user).getContent();
        } else {
            // TODO
            return null;
        }
    }
}

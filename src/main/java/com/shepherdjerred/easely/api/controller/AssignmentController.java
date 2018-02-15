package com.shepherdjerred.easely.api.controller;

import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.provider.Provider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class AssignmentController {

    private Provider provider;

    public Collection<Assignment> getAssignmentsForUser(User user) {
        return provider.getAssignmentsForUser(user);
    }
}

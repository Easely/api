package com.shepherdjerred.easely.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.provider.Provider;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

import static spark.Spark.get;

@Log4j2
public class AssignmentController implements Controller {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Provider provider;

    public AssignmentController(Provider provider) {
        this.provider = provider;
    }

    @Override
    public void setupRoutes() {
        get("/api/assignments", (request, response) -> {
            response.type("application/json");

            Collection<Assignment> assignments = provider.getAssignments().keySet();
            return objectMapper.writeValueAsString(assignments);
        });
    }
}

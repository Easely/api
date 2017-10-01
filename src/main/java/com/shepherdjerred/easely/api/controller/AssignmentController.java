package com.shepherdjerred.easely.api.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepherdjerred.easely.api.JWTHelper;
import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.storage.Store;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static spark.Spark.get;

@Log4j2
public class AssignmentController implements Controller {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Provider provider;
    private Store store;

    public AssignmentController(Store store, Provider provider) {
        this.store = store;
        this.provider = provider;
    }

    @Override
    public void setupRoutes() {
        get("/api/assignments", (request, response) -> {
            response.type("application/json");

            DecodedJWT jwt;
            try {
                jwt = JWTHelper.decodeJwt(request.headers("Authorization").replace("Bearer ", ""));
            } catch (JWTVerificationException e) {
                response.status(401);
                return "";
            } catch (UnsupportedEncodingException e) {
                response.status(500);
                return "";
            }

            Optional<User> user = store.getUser(UUID.fromString(jwt.getClaim("uuid").asString()));

            Collection<Assignment> assignments = new ArrayList<>();

            if (user.isPresent()) {
                assignments = provider.getAssignments(user.get());
                log.debug(assignments.size());
            }

            return objectMapper.writeValueAsString(assignments);
        });
    }
}

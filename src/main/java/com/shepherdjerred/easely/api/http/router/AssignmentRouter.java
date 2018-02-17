package com.shepherdjerred.easely.api.http.router;

import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.http.controller.AssignmentController;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.provider.loader.Loader;
import com.shepherdjerred.easely.api.http.router.filters.AuthenticationFilter;
import com.shepherdjerred.easely.api.storage.Store;
import lombok.extern.log4j.Log4j2;

import static spark.Spark.*;

@Log4j2
public class AssignmentRouter implements Router {

    private AssignmentController assignmentController;
    private Store store;
    private EaselyConfig easelyConfig;

    public AssignmentRouter(Store store, Loader loader, EaselyConfig easelyConfig) {
        this.store = store;
        assignmentController = new AssignmentController(loader);
        this.easelyConfig = easelyConfig;
    }

    public void setupRoutes() {
        before("/api/assignments", new AuthenticationFilter(store, easelyConfig));

        get("/api/assignments", (request, response) -> {
            response.type("application/json");

            User user;

            if (request.attribute("user") instanceof User) {
                user = request.attribute("user");
            } else {
                log.debug("Error getting user attribute from request");
                internalServerError("Error getting user attribute from request");
                return "";
            }

            return assignmentController.getAssignmentsForUser(user);
        }, new JsonTransformer());
    }

}

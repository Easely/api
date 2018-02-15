package com.shepherdjerred.easely.api.router;

import com.shepherdjerred.easely.api.controller.CourseController;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.router.filters.AuthenticationFilter;
import com.shepherdjerred.easely.api.storage.Store;
import lombok.extern.log4j.Log4j2;

import static spark.Spark.*;

@Log4j2
public class CourseRouter implements Router {

    private CourseController courseController;
    private Store store;

    public CourseRouter(Store store, Provider provider) {
        this.store = store;
        courseController = new CourseController(provider);
    }

    public void setupRoutes() {
        before("/api/courses", new AuthenticationFilter(store));

        get("/api/courses", (request, response) -> {
            response.type("application/json");

            User user;

            if (request.attribute("user") instanceof User) {
                user = request.attribute("user");
            } else {
                log.debug("Error getting user attribute from request");
                internalServerError("Error getting user attribute from request");
                return "";
            }

            return courseController.getCoursesForUser(user);
        }, new JsonTransformer());
    }

}
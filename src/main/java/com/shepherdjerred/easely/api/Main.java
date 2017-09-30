package com.shepherdjerred.easely.api;

import com.shepherdjerred.easely.api.controller.AssignmentController;
import com.shepherdjerred.easely.api.controller.CourseController;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.EaselProvider;
import lombok.extern.log4j.Log4j2;

import static spark.Spark.port;

@Log4j2
public class Main {

    private static Provider provider;

    public static void main(String args[]) {
        setupProvider();
        // setupRoutes();
        provider.getAssignments();
        provider.getCourses();
    }

    private static void setupProvider() {
        provider = new EaselProvider();
    }

    private static void setupRoutes() {
        int port = getPort();
        port(port);

        new AssignmentController(provider).setupRoutes();
        new CourseController(provider).setupRoutes();
    }

    /**
     * Returns the port the application should listen on. It will first look for an environment variable named PORT,
     * otherwise it will return 8080
     * @return
     */
    private static int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String portVar = processBuilder.environment().get("PORT");
        if (portVar != null) {
            return Integer.parseInt(portVar);
        }
        return 8080;
    }

}

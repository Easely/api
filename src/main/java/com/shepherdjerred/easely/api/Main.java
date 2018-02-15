package com.shepherdjerred.easely.api;

import com.shepherdjerred.easely.api.config.Config;
import com.shepherdjerred.easely.api.config.EnviornmentVariableConfig;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.CachedEaselProvider;
import com.shepherdjerred.easely.api.router.AssignmentRouter;
import com.shepherdjerred.easely.api.router.CourseRouter;
import com.shepherdjerred.easely.api.router.UserRouter;
import com.shepherdjerred.easely.api.storage.Store;
import com.shepherdjerred.easely.api.storage.database.mysql.HikariMysqlDatabase;
import com.shepherdjerred.easely.api.storage.database.mysql.MysqlStore;
import lombok.extern.log4j.Log4j2;

import static spark.Spark.*;

@Log4j2
public class Main {

    private static Config config;
    private static Store store;
    private static Provider provider;

    public static void main(String args[]) {
        setupMysqlStorage();
        setupProvider();
        setupRoutes();
    }

    private static void setupConfig() {
        config = new EnviornmentVariableConfig();
    }

    private static void setupMysqlStorage() {
        HikariMysqlDatabase hikariMysqlDatabase = new HikariMysqlDatabase(config.getHikariConfig());
        hikariMysqlDatabase.migrate();

        store = new MysqlStore(hikariMysqlDatabase);
    }

    private static void setupProvider() {
        provider = new CachedEaselProvider();
    }

    private static void setupRoutes() {
        port(config.getServerPort());

        enableCors();

        new AssignmentRouter(store, provider).setupRoutes();
        new CourseRouter(store, provider).setupRoutes();
        new UserRouter(store).setupRoutes();
    }

    private static void enableCors() {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    }

}

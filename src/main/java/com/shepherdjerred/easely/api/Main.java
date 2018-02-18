package com.shepherdjerred.easely.api;

import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.config.EnvVarEaselyConfig;
import com.shepherdjerred.easely.api.easel.adapter.EaselAdapter;
import com.shepherdjerred.easely.api.easel.adapter.ScraperEaselAdapter;
import com.shepherdjerred.easely.api.easel.scraper.LiveEaselScraper;
import com.shepherdjerred.easely.api.http.router.AssignmentRouter;
import com.shepherdjerred.easely.api.http.router.CourseRouter;
import com.shepherdjerred.easely.api.http.router.UserRouter;
import com.shepherdjerred.easely.api.storage.database.HikariMysqlDatabase;
import com.shepherdjerred.easely.api.storage.MysqlStore;
import lombok.extern.log4j.Log4j2;

import static spark.Spark.*;

@Log4j2
public class Main {

    private static EaselyConfig easelyConfig;
    private static com.shepherdjerred.easely.api.storage.Store store;
    private static EaselAdapter easelAdapter;

    public static void main(String args[]) {
        easelyConfig = new EnvVarEaselyConfig();
        setupMysqlStore();
        easelAdapter = new ScraperEaselAdapter(new LiveEaselScraper());
        setupRoutes();
    }

    private static void setupMysqlStore() {
        HikariMysqlDatabase hikariMysqlDatabase = new HikariMysqlDatabase(easelyConfig.getHikariConfig());
        hikariMysqlDatabase.migrate();
        store = new MysqlStore(hikariMysqlDatabase);
    }

    private static void setupRoutes() {
        port(easelyConfig.getServerPort());

        enableCors();

        new AssignmentRouter(store, easelAdapter, easelyConfig).setupRoutes();
        new CourseRouter(store, easelAdapter, easelyConfig).setupRoutes();
        new UserRouter(store, easelyConfig).setupRoutes();
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

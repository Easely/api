package com.shepherdjerred.easely.api;

import com.shepherdjerred.easely.api.cache.ScraperCache;
import com.shepherdjerred.easely.api.cache.RedisScraperCache;
import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.config.EnvVarEaselyConfig;
import com.shepherdjerred.easely.api.provider.CacheProvider;
import com.shepherdjerred.easely.api.provider.Provider;
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

    private static EaselyConfig easelyConfig;
    private static Store store;
    private static Provider provider;
    private static ScraperCache scraperCache;

    public static void main(String args[]) {
        setupConfig();
        scraperCache = new RedisScraperCache(easelyConfig);
        setupMysqlStore();
        setupProvider();
        setupRoutes();
    }

    private static void setupConfig() {
        easelyConfig = new EnvVarEaselyConfig();
    }

    private static void setupMysqlStore() {
        HikariMysqlDatabase hikariMysqlDatabase = new HikariMysqlDatabase(easelyConfig.getHikariConfig());
        hikariMysqlDatabase.migrate();

        store = new MysqlStore(hikariMysqlDatabase);
    }

    private static void setupProvider() {
        provider = new CacheProvider(scraperCache);
    }

    private static void setupRoutes() {
        port(easelyConfig.getServerPort());

        enableCors();

        new AssignmentRouter(store, provider, easelyConfig).setupRoutes();
        new CourseRouter(store, provider, easelyConfig).setupRoutes();
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

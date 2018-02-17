package com.shepherdjerred.easely.api;

import com.shepherdjerred.easely.api.provider.scraper.cache.ScraperCache;
import com.shepherdjerred.easely.api.provider.scraper.cache.RedisScraperCache;
import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.config.EnvVarEaselyConfig;
import com.shepherdjerred.easely.api.provider.loader.ScraperCacheLoader;
import com.shepherdjerred.easely.api.provider.loader.Loader;
import com.shepherdjerred.easely.api.http.router.AssignmentRouter;
import com.shepherdjerred.easely.api.http.router.CourseRouter;
import com.shepherdjerred.easely.api.http.router.UserRouter;
import com.shepherdjerred.easely.api.storage.Store;
import com.shepherdjerred.easely.api.storage.database.mysql.HikariMysqlDatabase;
import com.shepherdjerred.easely.api.storage.database.mysql.MysqlStore;
import lombok.extern.log4j.Log4j2;

import static spark.Spark.*;

@Log4j2
public class Main {

    private static EaselyConfig easelyConfig;
    private static Store store;
    private static Loader loader;

    public static void main(String args[]) {
        easelyConfig = new EnvVarEaselyConfig();
        setupMysqlStore();
        ScraperCache scraperCache = new RedisScraperCache(easelyConfig);
        loader = new ScraperCacheLoader(scraperCache);
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

        new AssignmentRouter(store, loader, easelyConfig).setupRoutes();
        new CourseRouter(store, loader, easelyConfig).setupRoutes();
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

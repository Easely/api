package com.shepherdjerred.easely.api;

import com.shepherdjerred.easely.api.controller.AssignmentController;
import com.shepherdjerred.easely.api.controller.CourseController;
import com.shepherdjerred.easely.api.controller.UserController;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.CachedEaselProvider;
import com.shepherdjerred.easely.api.storage.Store;
import com.shepherdjerred.easely.api.storage.mysql.Database;
import com.shepherdjerred.easely.api.storage.mysql.MysqlStore;
import com.zaxxer.hikari.HikariConfig;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.port;

@Log4j2
public class Main {

    private static Store store;
    private static Provider provider;
    
    public static void main(String args[]) {
        setupMysqlStorage();
        setupProvider();
        setupRoutes();
    }

    private static void setupMysqlStorage() {
        HikariConfig hikariConfig = getHikariConfig();

        Database database = new Database(hikariConfig);
        database.migrate();

        store = new MysqlStore(database);
    }

    private static HikariConfig getHikariConfig() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("DATABASE_URL") != null) {
            HikariConfig hikariConfig = new HikariConfig();
            try {
                URI jdbUri = new URI(processBuilder.environment().get("DATABASE_URL"));
                String jdbcUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + String.valueOf(jdbUri.getPort()) + jdbUri.getPath();

                hikariConfig.setJdbcUrl(jdbcUrl);
                hikariConfig.setUsername(jdbUri.getUserInfo().split(":")[0]);
                hikariConfig.setPassword(jdbUri.getUserInfo().split(":")[1]);
                hikariConfig.setMaximumPoolSize(4);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return hikariConfig;
        } else {
            return new HikariConfig("hikari.properties");
        }
    }

    private static void setupProvider() {
        provider = new CachedEaselProvider();
    }

    private static void setupRoutes() {
        int port = getPort();
        port(port);

        new AssignmentController(store, provider).setupRoutes();
        new CourseController(store, provider).setupRoutes();
        new UserController(store).setupRoutes();
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

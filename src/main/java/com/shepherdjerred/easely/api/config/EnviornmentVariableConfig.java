package com.shepherdjerred.easely.api.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.URISyntaxException;

@Log4j2
public class EnviornmentVariableConfig implements Config {

    private ProcessBuilder processBuilder;

    public EnviornmentVariableConfig() {
        processBuilder = new ProcessBuilder();
    }

    @Override
    public int getServerPort() {
        String portVar = processBuilder.environment().get("PORT");
        if (portVar != null) {
            return Integer.parseInt(portVar);
        } else {
            log.warn("No PORT set in environment variables. Using 8080");
            return 8080;
        }
    }

    @Override
    public HikariConfig getHikariConfig() {
        if (processBuilder.environment().get("CLEARDB_DATABASE_URL") != null) {
            HikariConfig hikariConfig = new HikariConfig();
            try {
                URI jdbUri = new URI(processBuilder.environment().get("CLEARDB_DATABASE_URL"));
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
            log.warn("No CLEARDB_DATABASE_URL set in environment variables. Using hikari.properties");
            return new HikariConfig("hikari.properties");
        }
    }

    @Override
    public String getJwtIssuer() {
        String jwtIssuer = processBuilder.environment().get("JWT_ISSUER");
        if (jwtIssuer != null) {
            return jwtIssuer;
        } else {
            log.warn("No JWT_ISSUER set in environment variables. Using 'http://example.com'");
            return "http://example.com";
        }
    }

    @Override
    public String getJwtSecret() {
        String jwtSecret = processBuilder.environment().get("JWT_SECRET");
        if (jwtSecret != null) {
            return jwtSecret;
        } else {
            log.warn("No JWT_SECRET set in environment variables. Using 'SECRET'");
            return "SECRET";
        }
    }

    @Override
    public String getCrossOriginDomains() {
        return null;
    }
}

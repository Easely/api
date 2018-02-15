package com.shepherdjerred.easely.api.config;

import com.zaxxer.hikari.HikariConfig;

public interface Config {
    int getServerPort();
    HikariConfig getHikariConfig();
    String getJwtIssuer();
    String getJwtSecret();
    String getCrossOriginDomains();
}

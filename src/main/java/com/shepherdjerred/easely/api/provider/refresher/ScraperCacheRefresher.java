package com.shepherdjerred.easely.api.provider.refresher;

import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.provider.scraper.cache.ScraperCache;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ScraperCacheRefresher implements Refresher {

    private ScraperCache scraperCache;

    @Override
    public void refreshUserCourses(User user) {

    }

    @Override
    public void refreshUserAssignments(User user) {

    }
}


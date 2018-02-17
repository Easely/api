package com.shepherdjerred.easely.api.provider;

import com.shepherdjerred.easely.api.provider.loader.ScraperCacheLoader;
import com.shepherdjerred.easely.api.model.Assignment;
import com.shepherdjerred.easely.api.model.Course;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.provider.refresher.ScraperCacheRefresher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

@Log4j2
@AllArgsConstructor
public class ScraperCacheProvider implements Provider {

    private ScraperCacheLoader loader;
    private ScraperCacheRefresher refresher;

    @Override
    public ProviderContentStatus<Collection<Course>> getUserCourses(User user) {
        return null;
    }

    @Override
    public ProviderContentStatus<Collection<Assignment>> getUserAssignments(User user) {
        return null;
    }

}

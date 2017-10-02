package com.shepherdjerred.easely.api.provider.easel;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.AssignmentScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.CourseScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.LoginScraper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LiveEaselProvider implements Provider {

    private Map<String, String> login(User user) {
        Map<String, String> cookies;

        LoginScraper loginScraper = new LoginScraper();
        loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
        cookies = loginScraper.getCookies();

        return cookies;
    }

    @Override
    public Collection<Course> getCourses(User user) {
        Map<String, String> cookies = login(user);
        return new CourseScraper().getCourses(cookies);
    }

    @Override
    public Collection<Assignment> getAssignments(User user) {
        Collection<Assignment> assignments = new ArrayList<>();
        getCourses(user).forEach(course -> {
            getAssignments(user, course).forEach(assignment -> {
                assignments.add(assignment);
            });
        });
        return assignments;
    }

    @Override
    public Collection<Assignment> getAssignments(User user, Course course) {
        Map<String, String> cookies = login(user);
        return new AssignmentScraper().getAssignments(cookies, course);
    }
}

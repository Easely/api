package com.shepherdjerred.easely.api.easel.scraper.cache;

import com.shepherdjerred.easely.api.easel.scraper.Scraper;
import com.shepherdjerred.easely.api.easel.scraper.model.*;
import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;
import java.util.Map;

public class CachedScraper implements Scraper {
    @Override
    public Map<String, String> getUserEaselCookies(User user) {
        return null;
    }

    @Override
    public String getEaselUserId(User user) {
        return null;
    }

    @Override
    public Collection<CourseCore> getUserCourseCores(User user) {
        return null;
    }

    @Override
    public CourseDetails getCourseDetails(CourseCore courseCore) {
        return null;
    }

    @Override
    public CourseGrade getCourseGrade(User user, CourseCore courseCore) {
        return null;
    }

    @Override
    public Collection<AssignmentCore> getCourseAssignmentCores(CourseCore courseCore) {
        return null;
    }

    @Override
    public AssignmentDetails getAssignmentDetails(AssignmentCore assignmentCore) {
        return null;
    }

    @Override
    public AssignmentGrade getAssignmentGrade(User user, AssignmentCore assignmentCore) {
        return null;
    }
}

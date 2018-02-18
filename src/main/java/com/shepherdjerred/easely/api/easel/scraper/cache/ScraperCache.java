package com.shepherdjerred.easely.api.easel.scraper.cache;

import com.shepherdjerred.easely.api.CacheException;
import com.shepherdjerred.easely.api.easel.scraper.model.*;
import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;
import java.util.Map;

public interface ScraperCache {
    void setUserEaselCookies(User user, Map<String, String> cookies);

    void setEaselUserId(User user, String userEaselId);

    void setUserCourseCores(User user, Collection<CourseCore> courses);

    void setCourseDetails(CourseCore courseCore, CourseDetails courseDetails);

    void setUserCourseGrade(User user, CourseCore courseCore, CourseGrade courseGrade);

    void setCourseAssignmentCores(CourseCore courseCore, Collection<AssignmentCore> assignments);

    void setAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails);

    void setUserAssignmentGrade(User user, AssignmentCore assignmentCore, AssignmentGrade assignmentGrade);

    boolean hasUserEaselCookies(User user);

    boolean hasEaselUserId(User user);

    boolean hasUserCourseCores(User user);

    boolean hasCourseDetails(CourseCore courseCore);

    boolean hasUserCourseGrade(User user, CourseCore courseCore);

    boolean hasCourseAssignmentCores(CourseCore courseCore);

    boolean hasAssignmentDetails(AssignmentCore assignmentCore);

    boolean hasUserAssignmentGrade(User user, AssignmentCore assignmentCore);

    Map<String, String> getUserEaselCookies(User user) throws CacheException;

    String getEaselUserId(User user) throws CacheException;

    Collection<CourseCore> getUserCourseCores(User user) throws CacheException;

    CourseDetails getCourseDetails(CourseCore courseCore) throws CacheException;

    CourseGrade getCourseGrade(User user, CourseCore courseCore) throws CacheException;

    Collection<AssignmentCore> getCourseAssignmentCores(CourseCore courseCore) throws CacheException;

    AssignmentDetails getAssignmentDetails(AssignmentCore assignmentCore) throws CacheException;

    AssignmentGrade getAssignmentGrade(User user, AssignmentCore assignmentCore) throws CacheException;
}

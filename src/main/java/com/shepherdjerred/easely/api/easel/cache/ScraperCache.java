package com.shepherdjerred.easely.api.easel.cache;

import com.shepherdjerred.easely.api.easel.scraper.model.*;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.model.UserCourseGrade;

import java.util.Collection;
import java.util.Map;

public interface ScraperCache {
    void setUserEaselCookies(User user, Map<String, String> cookies);

    void setUserEaselId(User user, String userEaselId);

    void setUserCourseCores(User user, Collection<CourseCore> courses);

    void setCourseDetails(CourseCore courseCore, CourseDetails courseDetails);

    void setUserCourseGrade(User user, CourseCore courseCore, UserCourseGrade userCourseGrade);

    void setCourseAssignmentCores(CourseCore courseCore, Collection<AssignmentCore> assignments);

    void setAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails);

    void setUserAssignmentGrade(User user, AssignmentCore assignmentCore, UserAssignmentGrade userAssignmentGrade);

    boolean hasUserEaselCookies(User user);

    boolean hasUserEaselId(User user);

    boolean hasUserCourseCores(User user);

    boolean hasCourseDetails(CourseCore courseCore);

    boolean hasUserCourseGrade(User user, CourseCore courseCore);

    boolean hasCourseAssignmentCores(CourseCore courseCore);

    boolean hasAssignmentDetails(AssignmentCore assignmentCore);

    boolean hasUserAssignmentGrade(User user, AssignmentCore assignmentCore);

    Map<String, String> getUserEaselCookies(User user) throws CacheException;

    String getUserEaselId(User user) throws CacheException;

    Collection<CourseCore> getUserCourseCores(User user) throws CacheException;

    CourseDetails getCourseDetails(CourseCore courseCore) throws CacheException;

    UserCourseGrade getUserCourseGrade(User user, CourseCore courseCore) throws CacheException;

    Collection<AssignmentCore> getCourseAssignmentCores(CourseCore courseCore) throws CacheException;

    AssignmentDetails getAssignmentDetails(AssignmentCore assignmentCore) throws CacheException;

    UserAssignmentGrade getUserAssignmentGrade(User user, AssignmentCore assignmentCore) throws CacheException;
}

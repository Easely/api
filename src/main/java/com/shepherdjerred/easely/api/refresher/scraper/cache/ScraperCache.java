package com.shepherdjerred.easely.api.refresher.scraper.cache;

import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;

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

    Map<String, String> getUserEaselCookies(User user);

    String getEaselUserId(User user);

    Collection<CourseCore> getUserCourseCores(User user);

    CourseDetails getCourseDetails(CourseCore courseCore);

    CourseGrade getCourseGrade(User user, CourseCore courseCore);

    Collection<AssignmentCore> getCourseAssignmentCores(CourseCore courseCore);

    AssignmentDetails getAssignmentDetails(AssignmentCore assignmentCore);

    AssignmentGrade getAssignmentGrade(User user, AssignmentCore assignmentCore);
}

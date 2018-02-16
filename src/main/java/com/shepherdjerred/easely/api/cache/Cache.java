package com.shepherdjerred.easely.api.cache;

import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;

import java.util.Collection;
import java.util.Map;

public interface Cache {
    void saveUserEaselCookies(User user, Map<String, String> cookies);

    void saveEaselUserId(User user, String userId);

    void saveUserCourses(User user, Collection<CourseCore> courses);

    void saveCourseDetails(CourseCore courseCore, CourseDetails courseDetails);

    void saveUserCourseGrade(User user, CourseCore courseCore, CourseGrade courseGrade);

    void saveCourseAssignments(CourseCore courseCore, Collection<AssignmentCore> assignments);

    void saveAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails);

    void saveUserAssignmentGrade(User user, AssignmentCore assignmentCore, AssignmentGrade assignmentGrade);

    boolean hasUserEaselCookies(User user);

    boolean hasEaselUserId(User user);

    boolean hasUserCourses(User user);

    boolean hasCourseDetails(CourseCore courseCore);

    boolean hasUserCourseGrade(User user, CourseCore courseCore);

    boolean hasCourseAssignments(CourseCore courseCore);

    boolean hasAssignmentDetails(AssignmentCore assignmentCore);

    boolean hasUserAssignmentGrade(User user, AssignmentCore assignmentCore);

    Map<String, String> loadUserEaselCookies(User user);

    String loadEaselUserId(User user);

    Collection<CourseCore> loadUserCourses(User user);

    CourseDetails loadCourseDetails(CourseCore courseCore);

    CourseGrade loadCourseGrade(User user, CourseCore courseCore);

    Collection<AssignmentCore> loadCourseAssignments(CourseCore courseCore);

    AssignmentDetails loadAssignmentDetails(AssignmentCore assignmentCore);

    AssignmentGrade loadAssignmentGrade(User user, AssignmentCore assignmentCore);
}

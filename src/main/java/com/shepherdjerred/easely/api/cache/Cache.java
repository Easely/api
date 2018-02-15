package com.shepherdjerred.easely.api.cache;

import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;

import java.util.Collection;
import java.util.Map;

public interface Cache {
    void saveUserEaselCookies(User user, Map<String, String> cookies);

    void saveUserId(User user, String userId);

    void saveUserCourses(User user, Collection<CourseCore> courses);

    void saveCourseDetails(CourseCore courseCore, CourseDetails courseDetails);

    void saveUserCourseGrade(User user, CourseCore courseCore, CourseGrade courseGrade);

    void saveCourseAssignments(CourseCore courseCore, Collection<AssignmentCore> assignments);

    void saveAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails);

    void saveUserAssignmentGrade(User user, AssignmentCore assignmentCore, AssignmentGrade assignmentGrade);
}

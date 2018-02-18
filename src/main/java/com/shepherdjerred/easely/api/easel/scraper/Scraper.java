package com.shepherdjerred.easely.api.easel.scraper;

import com.shepherdjerred.easely.api.easel.scraper.model.*;
import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;

import java.util.Collection;
import java.util.Map;

public interface Scraper {

    Map<String, String> getUserEaselCookies(User user);

    String getEaselUserId(User user);

    Collection<CourseCore> getUserCourseCores(User user);

    CourseDetails getCourseDetails(CourseCore courseCore);

    CourseGrade getCourseGrade(User user, CourseCore courseCore);

    Collection<AssignmentCore> getCourseAssignmentCores(CourseCore courseCore);

    AssignmentDetails getAssignmentDetails(AssignmentCore assignmentCore);

    AssignmentGrade getAssignmentGrade(User user, AssignmentCore assignmentCore);

}

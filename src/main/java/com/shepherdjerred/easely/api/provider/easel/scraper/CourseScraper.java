package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class CourseScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String CLASS_LIST_URL = BASE_URL + "/cgi-bin/user";

    public Collection<Course> getCourses(User user) {
        Collection<Course> courses = new ArrayList<>();

        log.debug("LOADING COURSES");

        try {
            // Login to EASEL
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
            Map<String, String> cookies = loginScraper.getCookies();

            // Load the page with classes
            Connection.Response homePage = Jsoup.connect(CLASS_LIST_URL)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element classList = homePage.parse().select("body > div > table:nth-child(2) > tbody > tr:nth-child(2) > td > ul").first();

            // Parse courses
            for (Element easelClass : classList.children()) {

                String classString = easelClass.child(0).text();

                String courseId;
                String courseCode;
                String courseName;

                String link = easelClass.child(0).attr("href");
                int lastEqualsIndex = link.lastIndexOf("=");
                courseId = link.substring(lastEqualsIndex + 1);

                int lastDashIndex = classString.lastIndexOf("–");
                courseCode = classString.substring(0, lastDashIndex - 1);
                courseName = classString.substring(lastDashIndex + 2);

                log.debug("LOADING COURSE FOR " + courseId);

                // Probably not the best way to do this, but it works
                CourseDetailsScraper courseDetailsScraper = new CourseDetailsScraper();
                courseDetailsScraper.loadCourseDetails(user, courseId);

                Course course = new Course(courseId, courseCode, courseName, courseDetailsScraper.getTeacher(), courseDetailsScraper.getResources());
                courses.add(course);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

}

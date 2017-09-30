package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.Course;
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
    private static final String LOGIN_URL = BASE_URL + "/cgi-bin/proc_login";
    private static final String GRADES_URL = BASE_URL + "/cgi-bin/user";

    private Map<String, String> cookies;

    public Collection<Course> getCourses() {
        Collection<Course> courses = new ArrayList<>();

        try {
            Connection.Response loginResponse = Jsoup.connect(LOGIN_URL)
                    .data("user", "jshepherd")
                    .data("passwd", "password")
                    .method(Connection.Method.POST)
                    .execute();

            cookies = loginResponse.cookies();

            Connection.Response homePage = Jsoup.connect(GRADES_URL)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element classList = homePage.parse().select("body > div > table:nth-child(2) > tbody > tr:nth-child(2) > td > ul").first();

            for (Element easelClass : classList.children()) {
                String classString = easelClass.child(0).text();

                String courseId;
                String courseCode;
                String courseName;

                String link = easelClass.child(0).attr("href");
                int lastEqualsIndex = link.lastIndexOf("=");
                courseId = link.substring(lastEqualsIndex);

                int lastDashIndex = classString.lastIndexOf("–");
                courseCode = classString.substring(0, lastDashIndex - 1);
                courseName = classString.substring(lastDashIndex + 2);

                Course course = new Course(courseId, courseCode, courseName);
                courses.add(course);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

}

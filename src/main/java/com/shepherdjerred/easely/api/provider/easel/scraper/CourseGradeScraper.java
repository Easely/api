package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.provider.easel.scraper.objects.CourseDetails;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class CourseGradeScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String CLASS_GRADES_URL = "/cgi-bin/details.php?class_id=";


    public CourseDetails loadCourseDetails(Map<String, String> cookies, String courseId, String userId) {
        try {


            log.debug("LOADING GRADES FOR " + courseId);

            // Load the page with classes
            Connection.Response classDetailsUrl = Jsoup.connect(BASE_URL + CLASS_GRADES_URL + courseId + "&sid=" + userId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            classDetailsUrl.parse().body().select("body > p:nth-child(4) > b > span");

            // TODO Load grades for all assignments, projects, exams, etc.

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

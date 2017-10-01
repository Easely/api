package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.User;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class CourseDetailsScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String CLASS_DETAILS_URL = "/cgi-bin/class?id=";

    @Getter
    private String teacher;
    @Getter
    private Map<String, String> resources;

    public CourseDetailsScraper() {
        resources = new HashMap<>();
    }

    public void loadCourseDetails(User user, String courseId) {
        try {
            // Login to EASEL
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
            Map<String, String> cookies = loginScraper.getCookies();

            log.debug("LOADING DETAILS FOR " + courseId);

            // Load the page with classes
            Connection.Response classDetailsUrl = Jsoup.connect(BASE_URL + CLASS_DETAILS_URL + courseId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element teacherElement = classDetailsUrl.parse().body().select("body > div > table.box.classInfo > tbody > tr:nth-child(2) > td > dl > dd:nth-child(12)").first();
            this.teacher = teacherElement.text();

            Element resourcesElement = classDetailsUrl.parse().body().select("body > div > table.classResources.box > tbody > tr:nth-child(2) > td > ul").first();

            // Not all classes have resources, check for null first
            if (resourcesElement != null) {
                resourcesElement.children().forEach(element -> {
                    String title = element.child(0).text();
                    String url = element.child(0).attr("href");
                    resources.put(title, url);
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.User;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class AssignmentGradeScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String ASSIGNMENT_DETAILS_URL = "/cgi-bin/view?id=";
    private static final String ASSIGNMENT_SUBMIT_URL = "&action=submit";

    @Getter
    private int possiblePoints;
    @Getter
    private int earnedPoints;
    @Getter
    private boolean isGraded;

    public void loadAssignmentGrade(User user, String assignmentId) {
        try {
            // Login to EASEL
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
            Map<String, String> cookies = loginScraper.getCookies();

            log.debug("LOADING DETAILS FOR " + assignmentId);

            // Load the page with classes
            Connection.Response classDetailsUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_DETAILS_URL + assignmentId + ASSIGNMENT_SUBMIT_URL)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element totalPointsElement = classDetailsUrl.parse().body().select("#points").first();
            String totalPointsText = totalPointsElement.text().replace(" Points", "");
            possiblePoints = Integer.valueOf(totalPointsText);

            Element earnedPointsElement = classDetailsUrl.parse().body().select("body > div").first();
            if (earnedPointsElement != null) {
                String earnedPointsText = earnedPointsElement.text().replace("Grade: ", "");
                earnedPoints = Integer.valueOf(earnedPointsText);
                isGraded = true;
            } else {
                earnedPoints = 0;
                isGraded = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

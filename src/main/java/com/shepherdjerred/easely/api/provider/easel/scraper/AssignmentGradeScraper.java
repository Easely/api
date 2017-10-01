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
    private static final String ASSIGNMENT_INFO_URL = "/cgi-bin/info?id=";
    private static final String ASSIGNMENT_SUBMIT_URL = "/cgi-bin/submit?id=";

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

            log.debug("LOADING INFO FOR " + assignmentId);

            // Load the page with classes
            Connection.Response classInfoUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_INFO_URL + assignmentId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element totalPointsElement = classInfoUrl.parse().select("#points").first();
            String totalPointsText = totalPointsElement.text().replace(" Points", "");
            possiblePoints = Integer.valueOf(totalPointsText);

            log.debug("LOADING GRADES FOR " + assignmentId);

            Connection.Response classGradesUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_SUBMIT_URL + assignmentId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element earnedPointsElement = classGradesUrl.parse().select("body > div").first();
            if (earnedPointsElement != null) {
                String earnedPointsText = earnedPointsElement.text().replace("Grade: ", "");
                // todo handle better
                if (earnedPointsText.equals("Submissions for this assignment are no longer being accepted")) {
                    return;
                }
                earnedPoints = Integer.valueOf(earnedPointsText.replaceAll("\\u00a0", "").replaceAll(" ", ""));
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

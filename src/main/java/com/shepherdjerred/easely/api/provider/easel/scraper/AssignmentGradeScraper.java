package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.AssignmentSubmission;
import com.shepherdjerred.easely.api.provider.easel.scraper.objects.AssignmentGrade;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class AssignmentGradeScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String ASSIGNMENT_INFO_URL = "/cgi-bin/info?id=";
    private static final String ASSIGNMENT_SUBMIT_URL = "/cgi-bin/submit?id=";

    public AssignmentGrade loadAssignmentGrade(Map<String, String> cookies, String assignmentId) {
        try {


            log.debug("LOADING INFO FOR " + assignmentId);

            // Load the page with classes
            Connection.Response classInfoUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_INFO_URL + assignmentId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element totalPointsElement = classInfoUrl.parse().select("#points").first();
            if (totalPointsElement != null) {
                String totalPointsText = totalPointsElement.text().replace(" Points", "");
                int possiblePoints = Integer.parseInt(totalPointsText);

                log.debug("LOADING GRADES FOR " + assignmentId);

                Connection.Response classGradesUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_SUBMIT_URL + assignmentId)
                        .cookies(cookies)
                        .method(Connection.Method.GET)
                        .execute();

                Element earnedPointsElement = classGradesUrl.parse().select("body > div").first();

                Collection<AssignmentSubmission> assignmentSubmissions = new ArrayList<>();

                Element noFilesElement = classGradesUrl.parse().select("body > h1").first();

                // TODO go through submissions table
                if (noFilesElement == null) {

                }

                int earnedPoints;
                boolean isGraded;
                if (earnedPointsElement != null) {
                    String earnedPointsText = earnedPointsElement.text().replace("Grade: ", "");
                    // TODO handle better
                    if (earnedPointsText.equals("Submissions for this assignment are no longer being accepted")) {
                        log.warn("Assignment grade not fetched");
                        return new AssignmentGrade(0, 0, false, assignmentSubmissions);
                    }
                    earnedPoints = Integer.parseInt(earnedPointsText.replaceAll("\\u00a0", "").replaceAll(" ", ""));
                    isGraded = true;
                } else {
                    earnedPoints = 0;
                    isGraded = false;
                }

                return new AssignmentGrade(possiblePoints, earnedPoints, isGraded, assignmentSubmissions);

            } else {
                // TODO handle better
                return new AssignmentGrade(0, 0, false, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.User;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Log4j2
public class AssignmentDetailsScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String ASSIGNMENT_DETAILS_URL = "/cgi-bin/view?id=";
    private static final String ASSIGNMENT_INFO_URL = "/cgi-bin/info?id=";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mma");

    @Getter
    private String attachmentUrl;
    @Getter
    private LocalTime dueTime;

    public void loadAssignmentDetails(User user, String assignmentId) {
        try {
            // Login to EASEL
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
            Map<String, String> cookies = loginScraper.getCookies();

            log.debug("LOADING DETAILS FOR " + assignmentId);

            Connection.Response assignmentInfoUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_INFO_URL + assignmentId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element dateElement = assignmentInfoUrl.parse().body().select("#date").first();

            String dateElementText = dateElement.text();
            int colonIndex = dateElementText.indexOf(":");

            String timeString = dateElementText.substring(colonIndex - 2);
            timeString = timeString.replace(" ", "0").toUpperCase();

            dueTime = LocalTime.parse(timeString, dateTimeFormatter);

            Connection.Response assignmentDetailsUrl = Jsoup.connect(BASE_URL + ASSIGNMENT_DETAILS_URL + assignmentId)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            log.debug(assignmentDetailsUrl.body());

            Element embedElement = assignmentDetailsUrl.parse().body().select("html > frameset > frame:nth-child(2)").first();
            attachmentUrl = embedElement.attr("src");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

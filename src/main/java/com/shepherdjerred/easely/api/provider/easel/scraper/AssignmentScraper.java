package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.GradedAssignment;
import com.shepherdjerred.easely.api.object.User;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class AssignmentScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Collection<Assignment> getAssignments(User user, Course course) {
        Collection<Assignment> assignments = new ArrayList<>();

        try {
            // Login to EASEL
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
            Map<String, String> cookies = loginScraper.getCookies();

            for (Assignment.Type type : Assignment.Type.values()) {
                String typeString = type.toString().toLowerCase();

                try {
                    Connection.Response assignmentListPage = Jsoup.connect(BASE_URL + "/cgi-bin/view?class_id=" + course.getId() + "&type=" + typeString)
                            .cookies(cookies)
                            .method(Connection.Method.GET)
                            .execute();

                    Element assignmentListElement = assignmentListPage.parse().select("body > table.box > tbody > tr:nth-child(2) > td > ul").first();

                    // No assignments
                    if (assignmentListElement != null) {

                        // Parse courses
                        for (Element assignmentElement : assignmentListElement.children()) {
                            String assignmentString = assignmentElement.child(0).text();

                            String id;
                            String name;
                            LocalDate dueDate;

                            String link = assignmentElement.child(0).attr("href");
                            int lastEqualsIndex = link.lastIndexOf("=") + 1;
                            id = link.substring(lastEqualsIndex);

                            dueDate = LocalDate.parse(assignmentString.substring(1, 11), dateTimeFormatter);

                            int endOfDate = assignmentString.indexOf(")");
                            String assignmentStringWithoutDate = assignmentString.substring(endOfDate);
                            int dash = assignmentStringWithoutDate.indexOf("-");
                            name = assignmentStringWithoutDate.substring(dash + 2);

                            Assignment assignment;
                            AssignmentDetailsScraper assignmentDetailsScraper = new AssignmentDetailsScraper();

                            assignmentDetailsScraper.loadAssignmentDetails(user, id);

                            String attachment = assignmentDetailsScraper.getAttachmentUrl();
                            LocalTime dueTime = assignmentDetailsScraper.getDueTime();

                            LocalDateTime localDateTime = dueDate.atTime(dueTime);

                            if (type == Assignment.Type.NOTES) {
                                assignment = new Assignment(id, name, localDateTime, type, course, attachment);
                            } else {
                                AssignmentGradeScraper assignmentGradeScraper = new AssignmentGradeScraper();

                                assignmentGradeScraper.loadAssignmentGrade(user, id);

                                int possiblePoints = assignmentGradeScraper.getPossiblePoints();
                                int earnedPoints = assignmentGradeScraper.getEarnedPoints();
                                boolean isGraded = assignmentGradeScraper.isGraded();

                                assignment = new GradedAssignment(id, name, localDateTime, type, course, attachment, possiblePoints, earnedPoints, isGraded);
                            }

                            log.debug(assignment);
                            assignments.add(assignment);
                        }
                    }
                } catch (UnsupportedMimeTypeException e) {
                    // TODO handle this properly
                    // This exception has been thrown when there is only one exam in a class. The server won't return a list of assignments like normal
                    // it will instead give you the file attached to the assignment (ie https://www.harding.edu/fmccown/classes/comp445-f17/review%20for%20exam%201%20fall17.pdf)
                    // We should still add this assignment somehow
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return assignments;
    }

}

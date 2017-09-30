package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class AssignmentScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Collection<Assignment> getAssignments(Course course) {
        Collection<Assignment> assignments = new ArrayList<>();

        try {
            // Login to EASEL
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login();
            Map<String, String> cookies = loginScraper.getCookies();

            // Load the page with homework
            Connection.Response homeworkPage = Jsoup.connect(BASE_URL + "/cgi-bin/view?class_id=" + course.getId() + "&type=homework")
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Element classList = homeworkPage.parse().select("body > table.box > tbody > tr:nth-child(2) > td > ul").first();

            // No assignments
            if (classList != null) {

                // Parse courses
                for (Element assignmentElement : classList.children()) {
                    String assignmentString = assignmentElement.child(0).text();

                    String id;
                    String name;
                    LocalDate dueDate;

                    String link = assignmentElement.child(0).attr("href");
                    int lastEqualsIndex = link.lastIndexOf("=");
                    id = link.substring(lastEqualsIndex);

                    dueDate = LocalDate.parse(assignmentString.substring(1, 11), dateTimeFormatter);

                    int dash = assignmentString.lastIndexOf("-");
                    name = assignmentString.substring(dash + 1);

                    Assignment assignment = new Assignment(id, name, dueDate, Assignment.Type.HOMEWORK);
                    assignments.add(assignment);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return assignments;
    }

}

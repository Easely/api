package com.shepherdjerred.easely.api.provider.easel.scraper;

import com.shepherdjerred.easely.api.object.CourseGrade;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class CourseGradeScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String CLASS_GRADES_URL = "/details.php?class_id=";


    public CourseGrade loadCourseGrades(Map<String, String> cookies, String courseId, String userId) {
        return new CourseGrade(25, 25, 25, 25, 82.5);
//        try {
//            log.debug("LOADING GRADES FOR " + courseId);
//
//            // Load the page with classes
//            Connection.Response classGradesUrl = Jsoup.connect(BASE_URL + CLASS_GRADES_URL + courseId + "&sid=" + 1673)
//                    .cookies(cookies)
//                    .method(Connection.Method.GET)
//                    .execute();
//
//            log.debug(classGradesUrl.url());
//            log.debug(classGradesUrl.body());
//
//            Element gradeTable = classGradesUrl.parse().select("#grade-table > tbody").first();
//            Elements rows = gradeTable.select("tr");
//
//            Map<Assignment.Type, Double> assignmentWeights = new HashMap<>();
//
//            Assignment.Type type = null;
//            for (Element row : rows) {
//                if (row.classNames().contains("category")) {
//                    String rowText = row.text();
//                    type = Assignment.Type.valueOf(rowText.toUpperCase());
//                } else if (row.children().size() == 1) {
//                    String rowText = row.text();
//                    if (rowText.contains("Weight")) {
//                        int lastSpace = rowText.lastIndexOf(' ');
//                        String weightText = rowText.substring(lastSpace, rowText.length() - 1);
//                        double weight = Double.valueOf(weightText);
//                        assignmentWeights.put(type, weight);
//                    }
//                }
//            }
//
//            Element averageElement = classGradesUrl.parse().body().select("body > p:nth-child(4) > b > span").first();
//            String averageString = averageElement.text().replace("%", "");
//            double average = Double.valueOf(averageString);
//
//            CourseGrade courseGrade = new CourseGrade(
//                    assignmentWeights.get(HOMEWORK),
//                    assignmentWeights.get(PROJECT),
//                    assignmentWeights.get(EXAM),
//                    assignmentWeights.get(FINAL),
//                    average
//            );
//
//            return courseGrade;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

}

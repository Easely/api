package com.shepherdjerred.easely.api.object;

import com.shepherdjerred.easely.api.provider.easel.scraper.objects.AssignmentCore;
import com.shepherdjerred.easely.api.provider.easel.scraper.objects.AssignmentDetails;
import com.shepherdjerred.easely.api.provider.easel.scraper.objects.AssignmentGrade;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GradedAssignment extends Assignment {
    @Getter
    private int possiblePoints;
    @Getter
    private int earnedPoints;
    @Getter
    private boolean isGraded;

    public GradedAssignment(String id, String name, LocalDateTime date, Type type, Course course, String attachment, int possiblePoints, int earnedPoints, boolean isGraded) {
        super(id, name, date, type, course, attachment);
        this.possiblePoints = possiblePoints;
        this.earnedPoints = earnedPoints;
        this.isGraded = isGraded;
    }

    public static GradedAssignment fromSubObjects(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails, AssignmentGrade gradedAssignment) {
        return new GradedAssignment(
                assignmentCore.getId(),
                assignmentCore.getName(),
                assignmentCore.getDate().atTime(assignmentDetails.getDueTime()),
                assignmentCore.getType(),
                assignmentCore.getCourse(),
                assignmentDetails.getAttachment(),
                gradedAssignment.getPossiblePoints(),
                gradedAssignment.getEarnedPoints(),
                gradedAssignment.isGraded()
        );
    }
}

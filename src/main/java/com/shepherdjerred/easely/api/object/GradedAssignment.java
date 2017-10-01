package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
public class GradedAssignment extends Assignment {
    @Getter
    private int possiblePoints;
    @Getter
    private int earnedPoints;
    @Getter
    private boolean isGraded;

    public GradedAssignment() {

    }

    public GradedAssignment(String id, String name, LocalDateTime date, Type type, Course course, String attachment, int possiblePoints, int earnedPoints, boolean isGraded) {
        super(id, name, date, type, course, attachment);
        this.possiblePoints = possiblePoints;
        this.earnedPoints = earnedPoints;
        this.isGraded = isGraded;
    }
}

package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class GradedAssignment extends Assignment {
    @Getter
    private final int possiblePoints;
    @Getter
    private final int earnedPoints;
    @Getter
    private final boolean isGraded;

    public GradedAssignment(String id, String name, LocalDate dueDate, Type type, int possiblePoints, int earnedPoints, boolean isGraded) {
        super(id, name, dueDate, type);
        this.possiblePoints = possiblePoints;
        this.earnedPoints = earnedPoints;
        this.isGraded = isGraded;
    }
}

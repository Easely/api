package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Assignment {
    @Getter
    private final String name;
    @Getter
    private final int possiblePoints;
    @Getter
    private final int earnedPoints;
    @Getter
    private final boolean isGraded;

    public Assignment(String name, int possiblePoints, int earnedPoints, boolean isGraded) {
        this.name = name;
        this.possiblePoints = possiblePoints;
        this.earnedPoints = earnedPoints;
        this.isGraded = isGraded;
    }
}

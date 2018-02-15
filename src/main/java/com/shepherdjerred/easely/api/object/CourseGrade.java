package com.shepherdjerred.easely.api.object;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseGrade {
    @Getter
    private double homeworkWeight;
    @Getter
    private double projectWeight;
    @Getter
    private double examWeight;
    @Getter
    private double finalWeight;
    @Getter
    private double classAverage;
}

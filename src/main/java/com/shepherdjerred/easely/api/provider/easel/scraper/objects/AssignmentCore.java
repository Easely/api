package com.shepherdjerred.easely.api.provider.easel.scraper.objects;

import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AssignmentCore {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private LocalDate date;
    @Getter
    private int number;
    @Getter
    private Assignment.Type type;
    @Getter
    private Course course;
}

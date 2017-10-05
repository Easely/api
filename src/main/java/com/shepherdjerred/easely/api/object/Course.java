package com.shepherdjerred.easely.api.object;

import com.shepherdjerred.easely.api.provider.easel.scraper.objects.CourseCore;
import com.shepherdjerred.easely.api.provider.easel.scraper.objects.CourseDetails;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Course {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String code;
    @Getter
    private String teacher;
    @Getter
    private Map<String, String> resources;

    public static Course fromSubObjects(CourseCore courseCore, CourseDetails courseDetails) {
        return new Course(
                courseCore.getId(),
                courseCore.getName(),
                courseCore.getCode(),
                courseDetails.getTeacher(),
                courseDetails.getResources()
        );
    }
}

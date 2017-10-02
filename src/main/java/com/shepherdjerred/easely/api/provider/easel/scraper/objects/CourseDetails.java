package com.shepherdjerred.easely.api.provider.easel.scraper.objects;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CourseDetails {
    @Getter
    private String teacher;
    @Getter
    private Map<String, String> resources;
}

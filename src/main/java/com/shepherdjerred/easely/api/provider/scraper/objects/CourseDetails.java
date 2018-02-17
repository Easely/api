package com.shepherdjerred.easely.api.provider.scraper.objects;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDetails {
    @Getter
    private String teacher;
    @Getter
    private Map<String, String> resources;
}

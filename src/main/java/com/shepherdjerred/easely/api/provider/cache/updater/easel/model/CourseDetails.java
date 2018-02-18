package com.shepherdjerred.easely.api.provider.cache.updater.easel.model;

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

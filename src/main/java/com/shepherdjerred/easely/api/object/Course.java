package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@EqualsAndHashCode
public class Course {
    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final String code;
    @Getter
    private final String teacher;
    @Getter
    private final Map<String, String> resources;

    public Course(String id, String name, String code, String teacher, Map<String, String> resources) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.teacher = teacher;
        this.resources = resources;
    }
}

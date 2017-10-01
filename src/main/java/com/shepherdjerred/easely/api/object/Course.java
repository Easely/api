package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

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

    public Course() {
    }

    public Course(String id, String name, String code, String teacher, Map<String, String> resources) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.teacher = teacher;
        this.resources = resources;
    }
}

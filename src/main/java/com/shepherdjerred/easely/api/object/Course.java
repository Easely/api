package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Course {
    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final String code;

    public Course(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}

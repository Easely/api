package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class Assignment {
    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final LocalDate date;
    @Getter
    private final Type type;
    @Getter
    private final Course course;

    public Assignment(String id, String name, LocalDate date, Type type, Course course) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.course = course;
    }

    public enum Type {
        HOMEWORK,
        NOTES,
        PROJECT,
        EXAM
    }
}

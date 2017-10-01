package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class Assignment {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private LocalDate date;
    @Getter
    private Type type;
    @Getter
    private Course course;

    public Assignment() {
    }

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

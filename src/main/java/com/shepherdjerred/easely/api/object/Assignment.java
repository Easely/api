package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
public class Assignment {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private LocalDateTime date;
    @Getter
    private Type type;
    @Getter
    private Course course;
    @Getter
    private String attachment;

    public Assignment() {
    }

    public Assignment(String id, String name, LocalDateTime date, Type type, Course course, String attachment) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.course = course;
        this.attachment = attachment;
    }

    public enum Type {
        HOMEWORK,
        NOTES,
        PROJECT,
        EXAM
    }
}

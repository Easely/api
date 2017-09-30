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
    private final LocalDate dueDate;
    @Getter
    private final Type type;

    public Assignment(String id, String name, LocalDate dueDate, Type type) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.type = type;
    }

    public enum Type {
        HOMEWORK,
        NOTE,
        PROJECT,
        EXAM
    }
}

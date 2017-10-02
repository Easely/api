package com.shepherdjerred.easely.api.object;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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

    public enum Type {
        HOMEWORK,
        NOTES,
        PROJECT,
        EXAM
    }
}

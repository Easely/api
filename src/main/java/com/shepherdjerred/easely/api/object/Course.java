package com.shepherdjerred.easely.api.object;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
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
}

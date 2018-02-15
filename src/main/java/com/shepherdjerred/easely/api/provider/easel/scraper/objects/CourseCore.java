package com.shepherdjerred.easely.api.provider.easel.scraper.objects;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseCore {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String code;
}

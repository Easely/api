package com.shepherdjerred.easely.api.provider.easel.scraper.objects;

import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignmentDetails {
    @Getter
    private LocalTime dueTime;
    @Getter
    private String attachment;
}

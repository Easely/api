package com.shepherdjerred.easely.api.object;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AssignmentSubmission {
    @Getter
    private String fileName;
    @Getter
    private String currentFile;
    @Getter
    private LocalDateTime currentFileTimestamp;
    @Getter
    private boolean isSubmitted;
}

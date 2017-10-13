package com.shepherdjerred.easely.api.provider.easel.scraper.objects;

import com.shepherdjerred.easely.api.object.AssignmentSubmission;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AssignmentGrade {
    @Getter
    private int possiblePoints;
    @Getter
    private int earnedPoints;
    @Getter
    private boolean isGraded;
    @Getter
    private Collection<AssignmentSubmission> assignmentSubmissions;
}

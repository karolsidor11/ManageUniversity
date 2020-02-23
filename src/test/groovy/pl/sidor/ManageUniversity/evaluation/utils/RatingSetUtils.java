package pl.sidor.ManageUniversity.evaluation.utils;

import pl.sidor.ManageUniversity.evaluation.model.RatingSet;
import pl.sidor.ManageUniversity.schedule.model.Subject;

public class RatingSetUtils {

    public static RatingSet getModifyRatingSet() {
       return RatingSet.builder().id(1L)
                .subject(Subject.builder()
                        .name("Chemia")
                        .build())
                .build();
    }

    public static RatingSet getRatingSet() {
       return RatingSet.builder()
                .id(1L)
                .subject(Subject.builder()
                        .name("Matematyka")
                        .build())
                .build();
    }
}

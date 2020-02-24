package pl.sidor.ManageUniversity.evaluation.utils;

import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.utils.TestStudentData;

import java.util.Collections;

public class StudentRatingsCardUtils {

    private static Student getStudentByNameAndLastName() {
        return TestStudentData.prepareStudent();
    }

    private static StudentRatingsCard getStudentRatingCard() {
        return StudentRatingsCard.builder()
                .id(1L)
                .student(TestStudentData.prepareStudent())
                .groups(2.2)
                .ratingSetList(Collections.emptyList())
                .build();
    }

    private static StudentRatingsCard updateStudentRatingCard() {
        return StudentRatingsCard.builder()
                .id(1L)
                .student(TestStudentData.prepareStudent())
                .groups(3.2)
                .ratingSetList(Collections.emptyList())
                .build();
    }
}

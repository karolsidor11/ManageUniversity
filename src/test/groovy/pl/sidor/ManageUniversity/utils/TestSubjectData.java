package pl.sidor.ManageUniversity.utils;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class TestSubjectData {

    public static Subject prepareSubject(Lecturer lecturer) {
        return Subject.builder()
                .id(1L)
                .name("Java")
                .roomNumber(22)
                .lecturer((lecturer))
                .build();
    }
    public static Subject prepareSubject() {
        return Subject.builder()
                .id(1L)
                .name("Java")
                .roomNumber(22)
                .startTime(LocalTime.of(12,12))
                .endTime(LocalTime.of(15,11))
                .lecturer(null)
                .build();
    }
}

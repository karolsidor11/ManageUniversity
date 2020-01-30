package pl.sidor.ManageUniversity.schedule.utils;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.time.LocalTime;

public class SubejctUtils {

    public static Subject getSubject() {
        return Subject.builder()
                .id(1L)
                .name("Polski")
                .lecturer(getLecturer())
                .build();
    }

    public static Subject getAllSubject() {
        return Subject.builder()
                .id(1L)
                .name("Polski")
                .lecturer(getLecturer())
                .endTime(LocalTime.of(12, 00))
                .endTime(LocalTime.of(13, 00))
                .build();
    }
    private static Lecturer getLecturer(){
        return Lecturer.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .build();
    }
}

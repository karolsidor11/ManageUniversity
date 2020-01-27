package pl.sidor.ManageUniversity.lecturer.utils;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;

public class LecturerUtils {

    public static FindScheduleRequest getRequest() {
        return FindScheduleRequest.builder()
                .name("Jan")
                .lastName("Nowak")
                .weekNumber(12)
                .build();
    }

    public static Lecturer getLecturer() {
        return Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .grade("Doctor")
                .email("nowak@wp.pl")
                .build();
    }

    public static Subject getSubject() {
        return Subject.builder()
                .id(1L)
                .name("Polski")
                .lecturer(getLecturer())
                .build();
    }

    public static Schedule getSchedule() {
        return Schedule.builder()
                .id(1L)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .build();
    }

    public static Lecturer getLecturers() {
        return Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .build();
    }
}

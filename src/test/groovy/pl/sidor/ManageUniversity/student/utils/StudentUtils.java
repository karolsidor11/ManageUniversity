package pl.sidor.ManageUniversity.student.utils;

import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;

public class StudentUtils {

    public static Student getStudent() {
        return Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .phoneNumber(4534534)
                .build();
    }

    public static Student getStudents() {
       return Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .studentGroup(2.3)
                .build();
    }

    public static Student getStudent2() {
        return Student.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .build();
    }

    public static FindScheduleRequest getRequest() {
       return FindScheduleRequest.builder()
                .name("Karol")
                .lastName("Sidor")
                .weekNumber(12)
                .build();
    }

    public static Schedule getSchedule() {
        return Schedule.builder()
                .id(1L)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .studentGroup(2.3)
                .build();
    }
}

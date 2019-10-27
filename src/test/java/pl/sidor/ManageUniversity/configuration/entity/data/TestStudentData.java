package pl.sidor.ManageUniversity.configuration.entity.data;

import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.student.model.Student;

public class TestStudentData {

    public static Student prepareStudent() {
        return Student.builder()
                .id(1L).name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .studentGroup(2.2)
                .build();
    }

    public static Student prepareUpdateStudent(){
        return Student.builder()
                .id(1L)
                .name("Marek")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();
    }

    public static FindScheduleRequest preparecheduleRequest() {
        return FindScheduleRequest.builder()
                .name("Karol")
                .lastName("Sidor")
                .weekNumber(12)
                .build();
    }
}

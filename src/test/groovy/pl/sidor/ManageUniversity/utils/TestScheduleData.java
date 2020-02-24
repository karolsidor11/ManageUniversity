package pl.sidor.ManageUniversity.utils;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestScheduleData {

    public static Schedule prepareSchedule(Subject subject) {
        return Schedule.builder()
                .id(1L)
                .studentGroup(2.2)
                .dayOfWeek(Days.Poniedzialek)
                .subjects(Collections.singletonList(subject))
                .weekNumber(12)
                .build();
    }

    public  static FindScheduleRequest prepareScheduleRequest(Lecturer lecturer) {
        return FindScheduleRequest.builder()
                .name(lecturer.getName())
                .lastName(lecturer.getLastName())
                .weekNumber(12)
                .build();
    }

    public static Schedule prepareSchedule() {
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.builder().id(1L).build());

        return Schedule.builder().id(1L)
                .weekNumber(12)
                .dayOfWeek(Days.Poniedzialek)
                .subjects(subjectList)
                .studentGroup(2.2)
                .build();
    }

    public static Schedule prepareScheduleUpdate() {
        return Schedule.builder()
                .id(1L).dayOfWeek(Days.Poniedzialek)
                .subjects(Collections.singletonList(Subject.builder()
                        .id(1L).name("Polski")
                        .roomNumber(11)
                        .build()))
                .build();
    }

    public static ScheduleUpdate prepareScheduleUpdates(){
        return ScheduleUpdate.builder()
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(Subject.builder()
                        .id(1L)
                        .name("Polski")
                        .build())
                .build();
    }
}

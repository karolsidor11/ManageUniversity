package pl.sidor.ManageUniversity.schedule.utils;

import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleUtils {

    public static ScheduleUpdate prepareModifyRequest() {
         return ScheduleUpdate.builder()
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(prepareSubject())
                .build();
    }

    public static Subject prepareSubject() {
        return Subject.builder()
                .id(1L)
                .name("Polski")
                .roomNumber(21)
                .startTime(LocalTime.now())
                .endTime(LocalTime.of(12, 20))
                .build();
    }

    public static Schedule prepareSchedule() {
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(prepareSubject());
        return Schedule.builder()
                .id(1L)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(subjectList)
                .build();
    }

    public static Optional<Schedule> getSchedule(Days days) {
        return Optional.of(Schedule.builder()
                .id(1L)
                .dayOfWeek(days)
                .build());
    }
}

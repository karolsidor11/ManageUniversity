package pl.sidor.ManageUniversity.schedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepo extends CrudRepository<Schedule, Long> {

    void deleteByDayOfWeek(Days day);

    Optional<Schedule> findByDayOfWeek(Days day);

    List<Schedule> findByStudentGroupAndWeekNumber(Double studentGroup, Integer weekNumber);

    Schedule findByStudentGroup(Double group);

    List<Schedule> findBySubjects(Subject subject);

    Optional<Schedule> findByDayOfWeekAndWeekNumber(Days dayOfWeek, Integer weekNumber );
}

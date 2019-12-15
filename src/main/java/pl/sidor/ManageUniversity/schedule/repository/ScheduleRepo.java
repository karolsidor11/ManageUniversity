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

    void deleteByDayOfWeek(final Days day);

    Optional<Schedule> findByDayOfWeek(final Days day);

    List<Schedule> findByStudentGroupAndWeekNumber(final Double studentGroup, final Integer weekNumber);

    Schedule findByStudentGroup(final Double group);

    List<Schedule> findBySubjects(final Subject subject);

    Optional<Schedule> findByDayOfWeekAndWeekNumber(final Days dayOfWeek, final Integer weekNumber);
}

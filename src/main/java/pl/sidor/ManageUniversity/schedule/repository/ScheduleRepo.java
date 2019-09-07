package pl.sidor.ManageUniversity.schedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.Optional;

@Repository
public interface ScheduleRepo extends CrudRepository<Schedule, Long> {

    void deleteByDayOfWeek(Days day);

    Optional<Schedule> findByDayOfWeek(Days day);
}

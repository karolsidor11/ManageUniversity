package pl.sidor.ManageUniversity.schedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

@Repository
public interface ScheduleRepo extends CrudRepository<Schedule, Long> {
}

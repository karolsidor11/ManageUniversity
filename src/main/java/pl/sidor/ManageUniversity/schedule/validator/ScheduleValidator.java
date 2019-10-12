package pl.sidor.ManageUniversity.schedule.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import java.util.function.Predicate;
import static java.util.Optional.ofNullable;

@Component
public class ScheduleValidator implements Predicate<Days> {

    private ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleValidator(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public boolean test(Days days) {
      return   !ofNullable(scheduleRepo.findByDayOfWeek(days)).isPresent();
    }
}

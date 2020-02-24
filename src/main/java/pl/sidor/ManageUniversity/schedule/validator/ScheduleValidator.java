package pl.sidor.ManageUniversity.schedule.validator;

import lombok.AllArgsConstructor;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;

import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
public class ScheduleValidator implements Predicate<Days> {

    private final ScheduleRepo scheduleRepo;

    @Override
    public boolean test(final Days days) {
        return ofNullable(scheduleRepo.findByDayOfWeek(days)).isEmpty();
    }
}

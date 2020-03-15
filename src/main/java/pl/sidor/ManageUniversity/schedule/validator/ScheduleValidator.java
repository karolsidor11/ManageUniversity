package pl.sidor.ManageUniversity.schedule.validator;

import lombok.RequiredArgsConstructor;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class ScheduleValidator implements Predicate<Days> {

    private final ScheduleRepo scheduleRepo;

    @Override
    public boolean test(final Days days) {
        return scheduleRepo.findByDayOfWeek(days).isPresent();
    }
}

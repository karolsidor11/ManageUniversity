package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public Schedule create(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    @Override
    public Schedule getScheduleById(Long id) {
        return scheduleRepo.findById(id).get();
    }

    @Override
    public Schedule findByDay(Days day) {

        return scheduleRepo.findByDayOfWeek(day);
    }

    @Override
    public boolean deleteByID(Long id) {
        scheduleRepo.deleteById(id);
        return true;
    }

    @Override
    public void deleteByDay(Days day) {
         scheduleRepo.deleteByDayOfWeek(day);
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {

        Optional<Schedule> byDayOfWeek = Optional.ofNullable(scheduleRepo.findByDayOfWeek(schedule.getDayOfWeek()));

        Schedule.ScheduleBuilder builder = Schedule.builder();

        byDayOfWeek.ifPresent(getUpdateSchedule(schedule, builder));
        Schedule build = builder.dayOfWeek(schedule.getDayOfWeek()).build();

        return scheduleRepo.save(build);
    }

    private Consumer<Schedule> getUpdateSchedule(Schedule schedule, Schedule.ScheduleBuilder builder) {
        return schedule1 -> builder.id(schedule.getId()).subjects(schedule.getSubjects());
    }
}

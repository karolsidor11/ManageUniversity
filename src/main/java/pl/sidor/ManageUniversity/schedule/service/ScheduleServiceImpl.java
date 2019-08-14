package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;

import java.util.Optional;
import java.util.function.Consumer;

@Service
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
    public boolean deleteByID(Long id) {
        scheduleRepo.deleteById(id);
        return true;
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {

        Optional<Schedule> byId = scheduleRepo.findById(schedule.getId());

        Schedule.ScheduleBuilder builder = Schedule.builder();

        byId.ifPresent(getUpdateSchedule(schedule, builder));
        Schedule build = builder.build();

        return scheduleRepo.save(build);

//        return byId.get().builder()
//                .id(schedule.getId())
//                .subjects(schedule.getSubjects())
//                .build();
    }

    private Consumer<Schedule> getUpdateSchedule(Schedule schedule, Schedule.ScheduleBuilder builder) {
        return schedule1 -> builder.id(schedule.getId()).subjects(schedule.getSubjects());
    }
}

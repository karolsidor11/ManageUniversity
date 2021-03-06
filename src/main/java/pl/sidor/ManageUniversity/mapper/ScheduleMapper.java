package pl.sidor.ManageUniversity.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

@Component
public class ScheduleMapper {

    public static ScheduleDTO mapTo(Schedule schedule){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(schedule, ScheduleDTO.class);
    }
}

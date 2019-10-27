package pl.sidor.ManageUniversity.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

@Component
public class LecturerMapper {

    public static LecturerDTO mapTo(Lecturer lecturer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(lecturer, LecturerDTO.class);
    }
}




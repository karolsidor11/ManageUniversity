package pl.sidor.ManageUniversity.mapper;

import org.modelmapper.ModelMapper;
import pl.sidor.ManageUniversity.dto.SubjectDTO;
import pl.sidor.ManageUniversity.schedule.model.Subject;

public class SubjectMapper {

    public static SubjectDTO mapTo(Subject subject) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(subject, SubjectDTO.class);
    }
}

package pl.sidor.ManageUniversity.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.dto.SubjectDTO;
import pl.sidor.ManageUniversity.schedule.model.Subject;

@Component
public class SubjectMapper {

    public static SubjectDTO mapTo(Subject subject) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(subject, SubjectDTO.class);
    }
}

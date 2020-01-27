package pl.sidor.ManageUniversity.lecturer.converter;

import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;

public class LecturerConverter {

    public static Lecturer convertDtoToLecturer(Lecturer lecturer){
        LecturerDTO lecturerDTOS = LecturerMapper.mapTo(lecturer);
        return Lecturer.builder()
                .name(lecturerDTOS.getName())
                .lastName(lecturerDTOS.getLastName())
                .email(lecturerDTOS.getEmail())
                .grade(lecturerDTOS.getGrade())
                .build();
    }
}

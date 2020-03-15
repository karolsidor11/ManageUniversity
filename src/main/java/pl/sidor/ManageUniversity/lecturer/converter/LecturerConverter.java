package pl.sidor.ManageUniversity.lecturer.converter;

import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;

import java.util.Objects;

public class LecturerConverter {

    public static Lecturer convertDtoToLecturer(Lecturer lecturer) {
        return Objects.nonNull(lecturer) ? createLecturer(lecturer) : null;
    }

    public static Lecturer createLecturer(Lecturer actual, Lecturer updateLecturer) {
        actual.setId(updateLecturer.getId());
        actual.setName(updateLecturer.getName());
        actual.setLastName(updateLecturer.getLastName());
        actual.setEmail(updateLecturer.getEmail());
        actual.setPhoneNumber(updateLecturer.getPhoneNumber());
        actual.setAdres(updateLecturer.getAdres());
        actual.setGrade(updateLecturer.getGrade());
        actual.setSubject(updateLecturer.getSubject());

        return updateLecturer;
    }

    private static Lecturer createLecturer(Lecturer lecturer) {
        LecturerDTO lecturerDTOS = LecturerMapper.mapTo(lecturer);
        return Lecturer.builder()
                .name(lecturerDTOS.getName())
                .lastName(lecturerDTOS.getLastName())
                .email(lecturerDTOS.getEmail())
                .grade(lecturerDTOS.getGrade())
                .build();
    }
}

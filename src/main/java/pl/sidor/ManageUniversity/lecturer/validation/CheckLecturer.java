package pl.sidor.ManageUniversity.lecturer.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.lecturer.dto.LecturerDto;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CheckLecturer implements Predicate<Lecturer> {

    private LecturerRepo lecturerRepo;

    @Autowired
    public CheckLecturer(LecturerRepo lecturerRepo) {
        this.lecturerRepo = lecturerRepo;
    }

    @Override
    public boolean test(Lecturer lecturerDto) {

        return !(Optional.ofNullable(lecturerRepo.findByEmail(lecturerDto.getEmail())).isPresent());
    }
}

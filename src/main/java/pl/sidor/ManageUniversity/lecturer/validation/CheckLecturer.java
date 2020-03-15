package pl.sidor.ManageUniversity.lecturer.validation;

import lombok.RequiredArgsConstructor;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;

import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class CheckLecturer implements Predicate<Lecturer> {

    private final LecturerRepo lecturerRepo;

    @Override
    public boolean test(final Lecturer lecturerDto) {
        return ofNullable(lecturerRepo.findByEmail(lecturerDto.getEmail())).isEmpty();
    }
}

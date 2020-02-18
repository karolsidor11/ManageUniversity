package pl.sidor.ManageUniversity.lecturer.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.lecturer.converter.LecturerConverter;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Transactional
@AllArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepo lecturerRepo;

    @Override
    public LecturerResponse findById(final Long id) {
        Optional<Lecturer> lecturerById = lecturerRepo.findById(id);
        return LecturerResponse.prepareLecturerResponse(lecturerById, ResponseException.brakWykladowcy(id));
    }

    @Override
    public LecturerResponse findLecturerDTO(final Long id) {
        LecturerResponse lecturerResponse = findById(id);
        Lecturer actualLecturer = LecturerConverter.convertDtoToLecturer(lecturerResponse.getLecturer());
        return LecturerResponse.prepareLecturerResponse(Optional.ofNullable(actualLecturer), ResponseException.brakWykladowcy(id));
    }

    @Override
    public LecturerResponse create(final Lecturer lecturer) {
        if (isNull(lecturer.getName()) || isNull(lecturer.getEmail())) {
            return LecturerResponse.prepareLecturerResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        Optional<Lecturer> savedLecturer = Optional.of(lecturerRepo.save(lecturer));
        return LecturerResponse.prepareLecturerResponse(savedLecturer, ResponseException.pustyObiekt());
    }

    @Override
    public LecturerResponse update(final Lecturer lecturer) {
        LecturerResponse lecturerResponse = findById(lecturer.getId());
        return updateLecturerIfPresent(lecturerResponse, lecturer);
    }

    @Override
    public LecturerResponse delete(final Long id) {
        LecturerResponse lecturerResponse = findById(id);
        return deleteLecturerIfPresent(lecturerResponse, id);
    }

    private LecturerResponse updateLecturerIfPresent(LecturerResponse lecturerResponse, Lecturer lecturer) {
        if (nonNull(lecturerResponse.getLecturer())) {
            Optional<Lecturer> updateLecturer = Optional.of(
                    lecturerRepo.save(LecturerConverter.createLecturer(lecturerResponse.getLecturer(), lecturer)));
            return LecturerResponse.prepareLecturerResponse(updateLecturer, ResponseException.pustyObiekt());
        }
        return lecturerResponse;
    }

    private LecturerResponse deleteLecturerIfPresent(LecturerResponse lecturerResponse, Long id) {
        if (Objects.nonNull(lecturerResponse.getLecturer())) {
            lecturerRepo.deleteById(id);
            return LecturerResponse.builder().header(Header.getInstance()).build();
        }
        return lecturerResponse;
    }
}

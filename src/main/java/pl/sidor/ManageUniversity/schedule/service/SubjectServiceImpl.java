package pl.sidor.ManageUniversity.schedule.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.response.SubjectResponse;
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator;

import java.util.Objects;
import java.util.Optional;

@Transactional
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;
    private final SubjectValidator subjectValidator;
    private final LecturerRepo lecturerRepo;

    @Override
    public SubjectResponse getById(final Long id) {
        Optional<Subject> subjectRepoById = subjectRepo.findById(id);
        return SubjectResponse.prepareResponse(subjectRepoById, ResponseException.brakPrzedmiotu(id));
    }

    @Override
    public SubjectResponse save(final Subject subject) {
        SubjectResponse subjectResponse = validateSubjectBeforeSave(subject);
        if (subjectResponse.getError() != null) {
            return subjectResponse;
        }
        Subject saveSubject = subjectRepo.save(subject);
        return SubjectResponse.prepareResponse(Optional.of(saveSubject), ResponseException.pustyObiekt());
    }

    @Override
    public SubjectResponse delete(final Long id) {
        return deleteSubjectIsPresent(id);
    }

    @Override
    public SubjectResponse findByLecturer(final Long id) {
        Optional<Lecturer> byNameAndLastName = lecturerRepo.findById(id);
        if (byNameAndLastName.isEmpty()) {
            return SubjectResponse.prepareResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        Optional<Subject> byLecturer = subjectRepo.findByLecturer(byNameAndLastName.get());
        return SubjectResponse.prepareResponse(byLecturer, ResponseException.pustyObiekt());
    }

    private SubjectResponse validateSubjectBeforeSave(Subject subject) {
        if (!subjectValidator.test(subject)) {
            return SubjectResponse.prepareResponse(Optional.empty(), ResponseException.istniejePrzedmiot());
        }
        return SubjectResponse.builder().build();
    }

    private SubjectResponse deleteSubjectIsPresent(Long id) {
        SubjectResponse subjectById = getById(id);
        if (Objects.nonNull(subjectById.getSubject())) {
            subjectRepo.deleteById(id);
            return SubjectResponse.builder().header(Header.getInstance()).build();
        }
        return SubjectResponse.prepareResponse(Optional.empty(), ResponseException.brakPrzedmiotu(id));
    }
}

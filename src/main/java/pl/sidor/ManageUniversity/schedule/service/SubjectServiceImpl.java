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
        if (subjectRepoById.isEmpty()) {
            return ResponseException.brakPrzedmiotu(id);
        }
        return SubjectResponse.prepareSubjectResponse(subjectRepoById.get());
    }

    @Override
    public SubjectResponse save(final Subject subject) {
        if (!subjectValidator.test(subject)) {
            return ResponseException.istniejePrzedmiot();
        }
        Subject saveSubject = subjectRepo.save(subject);
        return SubjectResponse.prepareSubjectResponse(saveSubject);
    }

    @Override
    public SubjectResponse delete(final Long id) {
        SubjectResponse byId = getById(id);
        if (Objects.isNull(byId.getError())) {
            subjectRepo.deleteById(id);
            return SubjectResponse.builder().header(Header.getInstance()).build();
        }
        return ResponseException.brakPrzedmiotu(id);
    }

    @Override
    public SubjectResponse findByLecturer(final Long id) {
        Optional<Lecturer> byNameAndLastName = lecturerRepo.findById(id);
        if(byNameAndLastName.isEmpty()){
            return ResponseException.brakPrzedmiotu(id);
        }
        Optional<Subject> byLecturer = subjectRepo.findByLecturer(byNameAndLastName.get());
        return SubjectResponse.prepareSubjectResponse(byLecturer.get());
    }

    @Override
    public SubjectResponse findByLecturer(final Lecturer lecturer) {
        Optional<Subject> subjectOptional = subjectRepo.findByLecturer(lecturer);
        if(subjectOptional.isEmpty()){
            return ResponseException.brakPrzedmiotuDlaWykladowcy();
        }
        return SubjectResponse.prepareSubjectResponse(subjectOptional.get());
    }
}

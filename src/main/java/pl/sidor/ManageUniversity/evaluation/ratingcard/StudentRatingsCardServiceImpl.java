package pl.sidor.ManageUniversity.evaluation.ratingcard;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo;
import pl.sidor.ManageUniversity.evaluation.response.StudentRatingsCardResponse;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.of;

@Transactional
@AllArgsConstructor
public class StudentRatingsCardServiceImpl implements StudentRatingsCardService {

    private final StudentCardRepo studentCardRepo;
    private final StudentRepo studentRepo;

    @Override
    public StudentRatingsCardResponse findByID(final Long id) {
        Optional<StudentRatingsCard> studentCardRepoById = studentCardRepo.findById(id);
        return StudentRatingsCardResponse.prepareStudentRatingCardResponse(studentCardRepoById, ResponseException.pustyObiekt());
    }

    @Override
    public StudentRatingsCardResponse findByStudent(final String name, final String lastName) {
        Optional<Student> byNameAndLastName = studentRepo.findByNameAndLastName(name, lastName);
        if (byNameAndLastName.isPresent()) {
            Optional<StudentRatingsCard> byStudent = studentCardRepo.findByStudent(byNameAndLastName.get().getId());
            return StudentRatingsCardResponse.prepareStudentRatingCardResponse(byStudent, ResponseException.pustyObiekt());
        }
        return StudentRatingsCardResponse.prepareStudentRatingCardResponse(Optional.empty(), ResponseException.pustyObiekt());
    }

    @Override
    public StudentRatingsCardResponse createCard(final StudentRatingsCard studentRatingsCard) {
        if (Objects.isNull(studentRatingsCard)) {
            return StudentRatingsCardResponse.prepareStudentRatingCardResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        Optional<StudentRatingsCard> save = of(studentCardRepo.save(studentRatingsCard));
        return StudentRatingsCardResponse.prepareStudentRatingCardResponse(save, ResponseException.pustyObiekt());
    }

    @Override
    public StudentRatingsCardResponse updateCard(final StudentRatingsCard studentRatingsCard) {
        Optional<StudentRatingsCard> byId = studentCardRepo.findById(studentRatingsCard.getId());
        return getStudentRatingsCardResponse(studentRatingsCard, byId);
    }

    @Override
    public StudentRatingsCardResponse deleteCard(final Long id) {
        StudentRatingsCardResponse byID = findByID(id);
        if (Objects.nonNull(byID.getError())) {
            return byID;
        }
        studentCardRepo.delete(byID.getRatingSet());
        return StudentRatingsCardResponse.builder().header(Header.getInstance()).build();
    }

    private StudentRatingsCardResponse getStudentRatingsCardResponse(StudentRatingsCard studentRatingsCard, Optional<StudentRatingsCard> byId) {
        if (byId.isPresent()) {
            StudentRatingsCard studentRatingsCard1 = buildStudentRatingsCard(byId.get(), studentRatingsCard);
            StudentRatingsCard save = studentCardRepo.save(studentRatingsCard1);
            return StudentRatingsCardResponse.prepareStudentRatingCardResponse(Optional.of(save), ResponseException.pustyObiekt());
        }
        return StudentRatingsCardResponse.prepareStudentRatingCardResponse(Optional.empty(), ResponseException.pustyObiekt());
    }

    private StudentRatingsCard buildStudentRatingsCard(final StudentRatingsCard actual, final StudentRatingsCard updateCard) {
        actual.setId(updateCard.getId());
        actual.setYear(updateCard.getYear());
        actual.setTerm(updateCard.getTerm());
        actual.setGroups(updateCard.getGroups());
        actual.setStudent(updateCard.getStudent());
        return actual;
    }
}

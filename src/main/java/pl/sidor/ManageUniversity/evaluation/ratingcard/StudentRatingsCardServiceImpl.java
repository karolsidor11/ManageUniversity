package pl.sidor.ManageUniversity.evaluation.ratingcard;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.evaluation.ratingcard.StudentRatingsCardService;
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
public class StudentRatingsCardServiceImpl implements StudentRatingsCardService {

    private final StudentCardRepo studentCardRepo;
    private final StudentRepo studentRepo;

    @Override
    public StudentRatingsCard findByID(final Long id) throws Throwable {
        return studentCardRepo.findById(id)
                .orElseThrow(ExceptionFactory.incorrectStudentCardID(String.valueOf(id)));
    }

    @Override
    public StudentRatingsCard findByStudent(final String name,  final String lastName) throws Throwable {
        val student = studentRepo.findByNameAndLastName(name, lastName)
                .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));

        return of(studentCardRepo.findByStudent(student.getId())).orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public StudentRatingsCard createCard( final StudentRatingsCard studentRatingsCard) throws Throwable {
       return ofNullable(studentRatingsCard).map(studentRatingsCard1 -> studentCardRepo.save(studentRatingsCard))
               .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public StudentRatingsCard updateCard(final StudentRatingsCard studentRatingsCard) throws Throwable {
        Optional<StudentRatingsCard> byId = studentCardRepo.findById(studentRatingsCard.getId());
        Optional<StudentRatingsCard> studentRatingsCard2 = byId
                .map(studentRatingsCard1 -> buildStudentRatingsCard(byId.get(), studentRatingsCard));

         return studentRatingsCard2.map(studentRatingsCard1 -> studentCardRepo.save(studentRatingsCard2.get()))
                .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public boolean deleteCard( final Long id) {
        of(studentCardRepo.findById(id)).ifPresent(studentRatingsCard -> studentCardRepo.deleteById(id));
        return true;
    }

    private StudentRatingsCard buildStudentRatingsCard( final StudentRatingsCard actual,  final StudentRatingsCard updateCard) {
        actual.setId(updateCard.getId());
        actual.setYear(updateCard.getYear());
        actual.setTerm(updateCard.getTerm());
        actual.setGroups(updateCard.getGroups());
        actual.setStudent(updateCard.getStudent());

        return actual;
    }
}

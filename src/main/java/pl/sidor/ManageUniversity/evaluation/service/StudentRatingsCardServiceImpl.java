package pl.sidor.ManageUniversity.evaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.Optional;

import static java.util.Optional.of;

@Service
public class StudentRatingsCardServiceImpl implements StudentRatingsCardService {

    private StudentCardRepo studentCardRepo;
    private StudentRepo studentRepo;

    @Autowired
    public StudentRatingsCardServiceImpl(StudentCardRepo studentCardRepo, StudentRepo studentRepo) {
        this.studentCardRepo = studentCardRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public StudentRatingsCard findByID(Long id) throws Throwable {
        return of(studentCardRepo.findById(id))
                .orElseThrow(ExceptionFactory.incorrectStudentCardID(String.valueOf(id))).get();
    }

    @Override
    public StudentRatingsCard findByStudent(String name, String lastName) {
        Student byNameAndLastName = studentRepo.findByNameAndLastName(name, lastName).get();
        return studentCardRepo.findByStudent(byNameAndLastName.getId());
    }

    @Override
    public StudentRatingsCard createCard(StudentRatingsCard studentRatingsCard) {

        return studentCardRepo.save(studentRatingsCard);
    }

    @Override
    public StudentRatingsCard updateCard(StudentRatingsCard studentRatingsCard) {

        Optional<StudentRatingsCard> byId = studentCardRepo.findById(studentRatingsCard.getId());

        byId.map(studentRatingsCard1 -> buildStudentRatingsCard(byId.get(), studentRatingsCard));

        return studentCardRepo.save(studentRatingsCard);
    }

    @Override
    public boolean deleteCard(Long id) {
        of(studentCardRepo.findById(id)).ifPresent(studentRatingsCard -> studentCardRepo.deleteById(id));
        return true;
    }

    private StudentRatingsCard buildStudentRatingsCard(StudentRatingsCard actual, StudentRatingsCard updateCard) {

        actual.setId(updateCard.getId());
        actual.setYear(updateCard.getYear());
        actual.setTerm(updateCard.getTerm());
        actual.setGroup(updateCard.getGroup());
        actual.setStudent(updateCard.getStudent());

        return actual;
    }
}

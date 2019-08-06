package pl.sidor.ManageUniversity.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.Optional;

import static java.util.Optional.of;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;
    private CheckUniqeStudentPredicate checkUniqeStudentPredicate;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, CheckUniqeStudentPredicate checkUniqeStudentPredicate) {
        this.studentRepo = studentRepo;
        this.checkUniqeStudentPredicate = checkUniqeStudentPredicate;
    }

    @Override
    public Student findById(long id) {

        return studentRepo.findById(id).get();
    }

    @Override
    public Student create(Student student) throws UniversityException {

      return   of(studentRepo.save(student))
                .filter(students -> checkUniqeStudentPredicate.test(students))
                .orElseThrow(ExceptionFactory::studentInDatabase);
    }

    @Override
    public void update(Student student) {

        Optional<Student> byId = studentRepo.findById(student.getId());

        Student actualStudent = byId.get();

        Student.builder()
                .name(student.getName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .adres(student.getAdres())
                .date(student.getDate())
                .build();

        studentRepo.save(actualStudent);
    }

    @Override
    public void delete(long id) {
        studentRepo.deleteById(id);
    }
}

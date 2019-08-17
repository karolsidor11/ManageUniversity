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
import static java.util.Optional.ofNullable;

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
    public Student findById(Long id) throws Throwable {
        Optional<Student> byId = studentRepo.findById(id);
        if(!byId.isPresent()){
            throw  ExceptionFactory.incorrectStudentID(String.valueOf(id));
        }
        return byId.get();

    }

    @Override
    public Student create(Student student) throws Throwable {

        return of(student).filter(checkUniqeStudentPredicate)
                .map(student1 -> studentRepo.save(student))
                .orElseThrow(ExceptionFactory.studentInDatabase(student.getEmail()));
    }

    @Override
    public void update(Student student) throws UniversityException {

        Optional<Student> byId = studentRepo.findById(student.getId());

        if(!byId.isPresent()){
            throw ExceptionFactory.incorrectStudentID(String.valueOf(student.getId()));
        } else{
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
    }

    @Override
    public void delete(Long id) throws UniversityException {

        Optional<Student> byId = studentRepo.findById(id);
        byId.ifPresent(student -> studentRepo.deleteById(id));
        if (!byId.isPresent()) {
            throw ExceptionFactory.incorrectStudentID(String.valueOf(id));
        }
    }
}
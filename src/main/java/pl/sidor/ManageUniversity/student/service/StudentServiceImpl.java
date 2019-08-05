package pl.sidor.ManageUniversity.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.StudentException;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.StudentValidator;

import java.util.Optional;

import static java.util.Optional.of;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;
    private StudentValidator studentValidator;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, StudentValidator studentValidator) {
        this.studentRepo = studentRepo;
        this.studentValidator = studentValidator;
    }

    @Override
    public Student findById(long id) {

        return studentRepo.findById(id).get();
    }

    @Override
    public Student create(Student student) throws StudentException {

      return   of(studentRepo.save(student))
                .filter(students -> studentValidator.test(students))
                .orElseThrow(() -> new StudentException("W bazie istnieje Student o podanym emailu lub numerze telefonu."));
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

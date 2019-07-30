package pl.sidor.ManageUniversity.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.StudentException;
import pl.sidor.ManageUniversity.student.dao.StudentDao;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.StudentValidator;

import java.util.Optional;

@Service
@Transactional
public class StudentService implements StudentDao {

    private StudentRepo studentRepo;
    private StudentValidator studentValidator;

    @Autowired
    public StudentService(StudentRepo studentRepo, StudentValidator studentValidator) {
        this.studentRepo = studentRepo;
        this.studentValidator = studentValidator;
    }

    @Override
    public Student findById(long id) {

        return studentRepo.findById(id).get();
    }

    @Override
    public Student create(Student student) throws StudentException {
        if (!studentValidator.test(student)) {
            return studentRepo.save(student);
        } else {
            throw new StudentException("W bazie istnieje Student o podanym emailu lub numerze telefonu.");
        }
    }

    @Override
    public void update(Student student) {

        Optional<Student> byId = studentRepo.findById(student.getId());

        Student actualStudent = byId.get();

        actualStudent.setName(student.getName());
        actualStudent.setLastName(student.getLastName());
        actualStudent.setEmail(student.getEmail());
        actualStudent.setPhoneNumber(student.getPhoneNumber());
        actualStudent.setAdres(student.getAdres());
        actualStudent.setDate(student.getDate());

        studentRepo.save(actualStudent);
    }

    @Override
    public void delete(long id) {
        studentRepo.deleteById(id);
    }
}

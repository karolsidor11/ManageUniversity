package pl.sidor.ManageUniversity.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.StudentNotFoundException;
import pl.sidor.ManageUniversity.student.dao.StudentDao;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.Optional;

@Service
@Transactional
public class StudentService implements StudentDao {

    private StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student findById(long id)  {

        return studentRepo.findById(id).get();
    }

    @Override
    public Student create(Student student) {
        return studentRepo.save(student);
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
    }

    @Override
    public void delete(long id) {
        studentRepo.deleteById(id);
    }
}

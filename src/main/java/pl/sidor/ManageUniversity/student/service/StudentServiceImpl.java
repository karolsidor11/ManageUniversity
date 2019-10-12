package pl.sidor.ManageUniversity.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
      return  studentRepo.findById(id).orElseThrow(ExceptionFactory.incorrectStudentID(id));
    }

    @Override
    public Student findByNameAndLastName(String name, String lastName) throws Throwable {
      return   of(studentRepo.findByNameAndLastName(name, lastName))
              .orElseThrow(ExceptionFactory.incorectStudentName(name, lastName));
    }

    @Override
    public Student create(Student student) throws Throwable {
        return ofNullable(student)
                .filter(checkUniqeStudentPredicate)
                .map(student1 -> studentRepo.save(student))
                .orElseThrow(ExceptionFactory.studentInDatabase(student.getEmail()));
    }

    @Override
    public void update(Student student) throws Throwable {

        Student student1 = of(findById(student.getId()))
                .map(studentOld -> buildStudnet(studentOld,student))
                .orElseThrow(() -> ExceptionFactory.incorrectStudentID(student.getId()));

        studentRepo.save(student1);

    }

    private Student buildStudnet(Student studentOld, Student newStudent) {
       studentOld.setId(newStudent.getId());
       studentOld.setName(newStudent.getName());
       studentOld.setName(newStudent.getLastName());
       studentOld.setName(newStudent.getEmail());
        return  newStudent;
    }

    @Override
    public void delete(Long id) throws Throwable {
        of(findById(id)).ifPresent(student -> studentRepo.deleteById(id));
    }
}
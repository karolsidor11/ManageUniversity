package pl.sidor.ManageUniversity.student.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.response.StudentResponse;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.Objects;
import java.util.Optional;

@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final CheckUniqeStudentPredicate checkUniqeStudentPredicate;

    @Override
    public StudentResponse findById(final Long id) {
        Optional<Student> student = studentRepo.findById(id);
        return StudentResponse.prepareStudentResponse(student, ResponseException.brakStudenta(id));
    }

    @Override
    public StudentResponse findByNameAndLastName(final String name, final String lastName) {
        Optional<Student> studentByNameAndLastName = studentRepo.findByNameAndLastName(name, lastName);
        return StudentResponse.prepareStudentResponse(studentByNameAndLastName, ResponseException.brakStudenta(name, lastName));
    }

    @Override
    public StudentResponse create(final Student student) {
        if (checkStudentInDatabseByEmailAndPhoneNumber(student)) {
            return StudentResponse.prepareStudentResponse(Optional.empty(), ResponseException.istniejeStudent(student));
        }
        Optional<Student> saveStudent = Optional.of(studentRepo.save(student));
        return StudentResponse.prepareStudentResponse(saveStudent, ResponseException.istniejeStudent(student));
    }

    @Override
    public StudentResponse update(final Student student) {
        StudentResponse studentResponse = findById(student.getId());
        Optional<Student> newStudent = Optional.of(studentRepo.save(buildStudent(studentResponse.getStudent(), student)));
        return StudentResponse.prepareStudentResponse(newStudent, ResponseException.brakStudenta(student.getId()));
    }

    @Override
    public StudentResponse delete(final Long id) {
        StudentResponse studentResponse = findById(id);
        return deleteStudentIfPresent(studentResponse, id);
    }

    private StudentResponse deleteStudentIfPresent(StudentResponse studentResponse, Long id) {
        if (Objects.nonNull(studentResponse.getStudent())) {
            studentRepo.deleteById(id);
            return StudentResponse.builder().header(Header.getInstance()).build();
        }
        return studentResponse;
    }

    private Student buildStudent(Student studentOld, Student newStudent) {
        studentOld.setId(newStudent.getId());
        studentOld.setName(newStudent.getName());
        studentOld.setName(newStudent.getLastName());
        studentOld.setName(newStudent.getEmail());
        return newStudent;
    }

    private boolean checkStudentInDatabseByEmailAndPhoneNumber(Student student) {
        return checkUniqeStudentPredicate.test(student);
    }
}

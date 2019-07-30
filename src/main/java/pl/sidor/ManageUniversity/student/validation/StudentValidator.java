package pl.sidor.ManageUniversity.student.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class StudentValidator implements Predicate<Student> {

    private StudentRepo studentRepo;

    @Autowired
    public StudentValidator(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public boolean test(Student student) {

        return emialIsUniqe(student.getEmail()) && phoneNumberIsUniqe(student.getPhoneNumber());
    }

    private boolean emialIsUniqe(String email) {
        return Optional.ofNullable(studentRepo.findByEmail(email)).isPresent();
    }

    private boolean phoneNumberIsUniqe(int phoneNumber) {
        return Optional.ofNullable(studentRepo.findByPhoneNumber(phoneNumber)).isPresent();
    }
}

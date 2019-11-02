package pl.sidor.ManageUniversity.student.validation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.Optional;
import java.util.function.Predicate;

@AllArgsConstructor
public class CheckUniqeStudentPredicate implements Predicate<Student> {

    private StudentRepo studentRepo;

    @Override
    public boolean test(Student student) {

        return (emialIsUniqe(student.getEmail()) && phoneNumberIsUniqe(student.getPhoneNumber()));
    }

    private boolean emialIsUniqe(String email) {
        return !Optional.ofNullable(studentRepo.findByEmail(email)).isPresent();
    }

    private boolean phoneNumberIsUniqe(int phoneNumber) {
        return !Optional.ofNullable(studentRepo.findByPhoneNumber(phoneNumber)).isPresent();
    }
}

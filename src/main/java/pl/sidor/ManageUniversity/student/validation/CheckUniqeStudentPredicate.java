package pl.sidor.ManageUniversity.student.validation;

import lombok.AllArgsConstructor;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
public class CheckUniqeStudentPredicate implements Predicate<Student> {

    private final StudentRepo studentRepo;

    @Override
    public boolean test(final Student student) {
        return (emialIsUniqe(student.getEmail()) && phoneNumberIsUniqe(student.getPhoneNumber()));
    }

    private boolean emialIsUniqe(final String email) {
        return ofNullable(studentRepo.findByEmail(email)).isEmpty();
    }

    private boolean phoneNumberIsUniqe(final int phoneNumber) {
        return ofNullable(studentRepo.findByPhoneNumber(phoneNumber)).isEmpty();
    }
}

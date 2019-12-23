package pl.sidor.ManageUniversity.student.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.student.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {

    List<Student> findByEmail(final String email);

    List<Student> findByPhoneNumber(final Integer phoneNumber);

    Optional<Student> findByNameAndLastName(final String name, final String lastName);

}

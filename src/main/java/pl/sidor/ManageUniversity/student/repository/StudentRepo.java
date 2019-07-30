package pl.sidor.ManageUniversity.student.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.student.model.Student;

import java.util.List;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {

    List<Student> findByEmail(String email);

    List<Student> findByPhoneNumber(int phoneNumber);
}

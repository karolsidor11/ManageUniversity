package pl.sidor.ManageUniversity.student.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.student.model.Student;

import java.util.List;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {

    List<Student> findByEmail(final String email);

    List<Student> findByPhoneNumber(final Integer phoneNumber);

    Student findByNameAndLastName(final String name, final String lastName);

    @Modifying
    @Transactional
    @Query("DELETE FROM Student std where std.isStudent = false")
    Integer deleteAllNotStudents();

}

package pl.sidor.ManageUniversity.student.dao;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.StudentException;
import pl.sidor.ManageUniversity.student.model.Student;

@Component
public interface StudentDao {

    Student findById(long id) throws Exception;

    Student create(Student student) throws StudentException;

    void update(Student student);

    void delete(long id);
}


package pl.sidor.ManageUniversity.student.dao;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.student.model.Student;

@Component
public interface StudentDao {

    Student findById(long id) throws Exception;

    Student create(Student student);

    void update(Student student);

    void delete(long id);
}


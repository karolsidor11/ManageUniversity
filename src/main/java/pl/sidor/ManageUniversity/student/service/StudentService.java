package pl.sidor.ManageUniversity.student.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.student.model.Student;

@Component
public interface StudentService {

    Student findById(long id);

    Student create(Student student) throws UniversityException;

    void update(Student student);

    void delete(long id);
}


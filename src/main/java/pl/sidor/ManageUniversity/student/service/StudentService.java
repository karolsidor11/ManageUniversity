package pl.sidor.ManageUniversity.student.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.student.model.Student;

@Component
public interface StudentService {

    Student findById(Long id) throws Throwable;

    Student create(Student student) throws Throwable;

    void update(Student student) throws UniversityException;

    void delete(Long id) throws UniversityException;
}


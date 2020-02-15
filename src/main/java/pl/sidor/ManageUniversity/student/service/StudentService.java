package pl.sidor.ManageUniversity.student.service;

import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.response.StudentResponse;

public interface StudentService {

    StudentResponse findById(final Long id);

    StudentResponse findByNameAndLastName(final String name, final String lastName);

    StudentResponse create(final Student student);

    StudentResponse update(final Student student);

    StudentResponse delete(final Long id);

}


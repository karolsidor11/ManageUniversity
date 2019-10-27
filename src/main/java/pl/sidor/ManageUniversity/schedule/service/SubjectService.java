package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.util.Optional;

public interface SubjectService {

    Subject getById(Long id) throws Throwable;

    Subject save(Subject subject) throws Throwable;

    void delete(Long id) throws Throwable;

    Optional<Subject> findByLecturer(Long id, String name, String lastName);

    Subject findByLecturer(Lecturer lecturer);
}

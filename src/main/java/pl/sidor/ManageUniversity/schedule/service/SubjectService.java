package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.util.Optional;

public interface SubjectService {

    Subject getById(final Long id) throws Throwable;

    Subject save(final Subject subject) throws Throwable;

    void delete(final Long id) throws Throwable;

    Optional<Subject> findByLecturer(final Long id, final String name, final String lastName) throws Throwable;

    Subject findByLecturer(final Lecturer lecturer) throws Throwable;
}

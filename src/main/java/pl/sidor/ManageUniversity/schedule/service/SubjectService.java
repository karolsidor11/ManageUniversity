package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.response.SubjectResponse;

public interface SubjectService {

    SubjectResponse getById(final Long id);

    SubjectResponse save(final Subject subject);

    SubjectResponse delete(final Long id);

    SubjectResponse findByLecturer(final Long id) ;

    SubjectResponse findByLecturer(final Lecturer lecturer);
}

package pl.sidor.ManageUniversity.lecturer.service;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;

public interface LecturerService {

    LecturerResponse findById(final Long id);

    LecturerResponse findLecturerDTO(final Long id);

    LecturerResponse create(final Lecturer lecturer);

    LecturerResponse update(final Lecturer lecturer);

    LecturerResponse delete(final Long id);
}

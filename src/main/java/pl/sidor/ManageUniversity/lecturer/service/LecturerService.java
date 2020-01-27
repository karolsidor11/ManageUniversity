package pl.sidor.ManageUniversity.lecturer.service;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

public interface LecturerService {

    LecturerResponse findById(final Long id);

    LecturerResponse findLecturerDTO(final Long id);

    LecturerResponse create(final Lecturer lecturer);

    LecturerResponse update(final Lecturer lecturer);

    LecturerResponse delete(final Long id);

    List<Schedule> findScheduleForLecturer(final FindScheduleRequest request) throws Throwable;

}

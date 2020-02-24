package pl.sidor.ManageUniversity.lecturer.service;

import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

public interface LecturerService {

    Lecturer findById(final Long id) throws Throwable;

    LecturerDTO findLecturerDTO(final Long id) throws Throwable;

    Lecturer create(final Lecturer lecturer) throws Throwable;

    void update(final Lecturer lecturer) throws Throwable;

    void delete(final Long id) throws Throwable;

    List<Schedule> findScheduleForLecturer(final FindScheduleRequest request) throws Throwable;

}

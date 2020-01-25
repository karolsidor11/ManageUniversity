package pl.sidor.ManageUniversity.student.service;

import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse findById(final Long id);

    StudentResponse findByNameAndLastName(final String name, final String lastName);

    StudentResponse create(final Student student);

    StudentResponse update(final Student student);

    StudentResponse delete(final Long id);

    List<Schedule> findScheduleForStudent(final FindScheduleRequest request) throws Throwable;

    List<ScheduleDTO> findSchedule(final FindScheduleRequest request) throws Throwable;
}


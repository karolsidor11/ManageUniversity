package pl.sidor.ManageUniversity.student.service;

import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;

import java.util.List;

public interface StudentService {

    Student findById(final Long id) throws Throwable;

    Student findByNameAndLastName(final String name, final String lastName) throws Throwable;

    Student create(final Student student) throws Throwable;

    void update(final Student student) throws Throwable;

    void delete(final Long id) throws Throwable;

    List<Schedule> findScheduleForStudent(final FindScheduleRequest request) throws Throwable;

    List<ScheduleDTO> findSchedule(final FindScheduleRequest request) throws Throwable;
}


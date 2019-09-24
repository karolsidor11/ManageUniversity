package pl.sidor.ManageUniversity.lecturer.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

@Component
public interface LecturerService {

    Lecturer findById(long id) throws Throwable;

    LecturerDTO findLecturerDTO(long id);

    Lecturer create(Lecturer lecturer) throws Throwable;

    void update(Lecturer lecturer) throws Throwable;

    void delete(long id) throws Throwable;

    List<Schedule> findScheduleForLecturer(FindScheduleRequest request) throws Throwable;

}

package pl.sidor.ManageUniversity.student.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;

import java.util.List;

@Component
public interface StudentService {

    Student findById(Long id) throws Throwable;

    Student findByNameAndLastName(String name, String lastName) throws Throwable;

    Student create(Student student) throws Throwable;

    void update(Student student) throws Throwable;

    void delete(Long id) throws Throwable;

   List<Schedule> findScheduleForStudent(String name, String lastName, Integer weekNumber) throws Throwable;
}


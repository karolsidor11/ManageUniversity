package pl.sidor.ManageUniversity.student.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.exception.*;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.mapper.ScheduleMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.response.StudentResponse;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final CheckUniqeStudentPredicate checkUniqeStudentPredicate;
    private final ScheduleRepo scheduleRepo;

    @Override
    public StudentResponse findById(final Long id) {
        Optional<Student> student = studentRepo.findById(id);
        if (student.isEmpty()) {
            return ResponseException.brakStudenta(id);
        }
        return  StudentResponse.prepareStudentResponse(student.get());
    }

    @Override
    public StudentResponse findByNameAndLastName(final String name, final String lastName) {
        Optional<Student> studentByNameAndLastName = studentRepo.findByNameAndLastName(name, lastName);
        if (studentByNameAndLastName.isEmpty()) {
            return ResponseException.brakStudenta(name, lastName);
        }
        return  StudentResponse.prepareStudentResponse(studentByNameAndLastName.get());
    }

    @Override
    public StudentResponse create(final Student student) {
        if (checkStudentInDatabseByEmailAndPhoneNumber(student)) {
            return ResponseException.istniejeStudent(student);
        }
        Student saveStudent = studentRepo.save(student);
        return StudentResponse.prepareStudentResponse(saveStudent);
    }

    @Override
    public StudentResponse update(final Student student)  {
        StudentResponse studentResponse = findById(student.getId());
        if (Objects.nonNull(studentResponse.getError())){
            return studentResponse;
        }
        Student newStudent = studentRepo.save(buildStudent(studentResponse.getStudent(), student));
        return StudentResponse.prepareStudentResponse(newStudent);
    }

    @Override
    public StudentResponse delete(final Long id) {
        StudentResponse studentResponse = findById(id);
        if (Objects.nonNull(studentResponse.getStudent())) {
            studentRepo.deleteById(id);
            return StudentResponse.builder().header(Header.getHeader()).build();
        }
        return studentResponse;
    }

    @Override
    public List<Schedule> findScheduleForStudent(final FindScheduleRequest request) throws Throwable {
        Student byNameAndLastName = findByNameAndLastName(request.getName(), request.getLastName()).getStudent();
        List<Schedule> schedules = scheduleRepo
                .findByStudentGroupAndWeekNumber(byNameAndLastName.getStudentGroup(), request.getWeekNumber());

        if (schedules.isEmpty()) {
            throw ExceptionFactory.nieoczekianyBladSystemu(request.getName(), request.getLastName(), request.getWeekNumber());
        }
        return schedules;
    }

    @Override
    public List<ScheduleDTO> findSchedule(final FindScheduleRequest request) throws Throwable {
        Student byNameAndLastName = findByNameAndLastName(request.getName(), request.getLastName()).getStudent();
        List<Schedule> byStudentGroupAndWeekNumber = scheduleRepo.
                findByStudentGroupAndWeekNumber(byNameAndLastName.getStudentGroup(), request.getWeekNumber());
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (byStudentGroupAndWeekNumber.isEmpty()) {
            throw ExceptionFactory.objectIsEmpty("!!!");
        }
        byStudentGroupAndWeekNumber.forEach((schedule) -> {
            ScheduleDTO scheduleDTO = ScheduleMapper.mapTo(schedule);
            scheduleDTOS.add(scheduleDTO);
        });

        return scheduleDTOS;
    }

    private Student buildStudent(Student studentOld, Student newStudent) {
        studentOld.setId(newStudent.getId());
        studentOld.setName(newStudent.getName());
        studentOld.setName(newStudent.getLastName());
        studentOld.setName(newStudent.getEmail());
        return newStudent;
    }

    private boolean checkStudentInDatabseByEmailAndPhoneNumber(Student student1) {
        return checkUniqeStudentPredicate.test(student1);
    }
}

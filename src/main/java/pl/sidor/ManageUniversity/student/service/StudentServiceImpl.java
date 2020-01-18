package pl.sidor.ManageUniversity.student.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.mapper.ScheduleMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final CheckUniqeStudentPredicate checkUniqeStudentPredicate;
    private final ScheduleRepo scheduleRepo;

    @Override
    public Student findById(final Long id) throws Throwable {
        return studentRepo.findById(id).orElseThrow(ExceptionFactory.incorrectStudentID(id));
    }

    @Override
    public Student findByNameAndLastName(final String name, final String lastName) throws Throwable {
        return studentRepo.findByNameAndLastName(name, lastName).orElseThrow(ExceptionFactory.incorectStudentName(name, lastName));
    }

    @Override
    public Student create(final Student student) throws Throwable {
        return ofNullable(student)
                .map(createStudent())
                .orElseThrow(()->ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public void update(final Student student) throws Throwable {
        Student actualStudent = ofNullable(findById(student.getId()))
                .map(studentOld -> buildStudnet(studentOld, student))
                .orElseThrow(() -> ExceptionFactory.incorrectStudentID(student.getId()));
        studentRepo.save(actualStudent);
    }

    private Student buildStudnet(Student studentOld, Student newStudent) {
        studentOld.setId(newStudent.getId());
        studentOld.setName(newStudent.getName());
        studentOld.setName(newStudent.getLastName());
        studentOld.setName(newStudent.getEmail());
        return newStudent;
    }

    @Override
    public void delete(final Long id) throws Throwable {
        of(findById(id)).ifPresent(student -> studentRepo.deleteById(id));
    }

    @Override
    public List<Schedule> findScheduleForStudent(final FindScheduleRequest request) throws Throwable {
        Student byNameAndLastName = findByNameAndLastName(request.getName(), request.getLastName());
        List<Schedule> schedules = scheduleRepo.findByStudentGroupAndWeekNumber
                (byNameAndLastName.getStudentGroup(), request.getWeekNumber());

        if (schedules.isEmpty()) {
            throw ExceptionFactory.nieoczekianyBladSystemu(request.getName(), request.getLastName(), request.getWeekNumber());
        }
        return schedules;
    }

    @Override
    public List<ScheduleDTO> findSchedule(final FindScheduleRequest request) throws Throwable {
        Student byNameAndLastName = findByNameAndLastName(request.getName(), request.getLastName());
        List<Schedule> byStudentGroupAndWeekNumber = scheduleRepo.findByStudentGroupAndWeekNumber(byNameAndLastName.getStudentGroup(), request.getWeekNumber());
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (byStudentGroupAndWeekNumber.isEmpty()) {
            throw ExceptionFactory.objectIsEmpty("!!!");
        }

        byStudentGroupAndWeekNumber.forEach((schedule)->{
            ScheduleDTO scheduleDTO = ScheduleMapper.mapTo(schedule);
            scheduleDTOS.add(scheduleDTO);
        });

        return scheduleDTOS;
    }

    private Function<Student, Student> createStudent() {
        return newStudent -> {
            checkStudent(newStudent);
            return studentRepo.save(newStudent);
        };
    }

    private void checkStudent(Student student1) {
        try {
            checkStudentInDatabaseByEmailAndPhoneNumber(student1);
        } catch (UniversityException e) {
            e.printStackTrace();
        }
    }

    private void checkStudentInDatabaseByEmailAndPhoneNumber(final Student student) throws UniversityException {
        if (checkUniqeStudentPredicate.test(student)) {
            throw ExceptionFactory.studentInDatabase(student.getEmail());
        }
    }
}

package pl.sidor.ManageUniversity.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.mapper.LecturerDTO;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final CheckUniqeStudentPredicate checkUniqeStudentPredicate;
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, CheckUniqeStudentPredicate checkUniqeStudentPredicate, ScheduleRepo scheduleRepo) {
        this.studentRepo = studentRepo;
        this.checkUniqeStudentPredicate = checkUniqeStudentPredicate;
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public Student findById(Long id) throws Throwable {
        return studentRepo.findById(id).orElseThrow(ExceptionFactory.incorrectStudentID(id));
    }

    @Override
    public Student findByNameAndLastName(String name, String lastName) throws Throwable {
        return studentRepo.findByNameAndLastName(name, lastName).orElseThrow(ExceptionFactory.incorectStudentName(name, lastName));

    }

    @Override
    public Student create(Student student) throws Throwable {
        if (Objects.isNull(student)) {
            throw ExceptionFactory.objectIsEmpty("!!!");
        }
        return ofNullable(student)
                .filter(checkUniqeStudentPredicate)
                .map(student1 -> studentRepo.save(student))
                .orElseThrow(ExceptionFactory.studentInDatabase(student.getEmail()));
    }

    @Override
    public void update(Student student) throws Throwable {

        Student student1 = of(findById(student.getId()))
                .map(studentOld -> buildStudnet(studentOld, student))
                .orElseThrow(() -> ExceptionFactory.incorrectStudentID(student.getId()));

        studentRepo.save(student1);
    }

    private Student buildStudnet(Student studentOld, Student newStudent) {
        studentOld.setId(newStudent.getId());
        studentOld.setName(newStudent.getName());
        studentOld.setName(newStudent.getLastName());
        studentOld.setName(newStudent.getEmail());
        return newStudent;
    }

    @Override
    public void delete(Long id) throws Throwable {
        of(findById(id)).ifPresent(student -> studentRepo.deleteById(id));
    }

    @Override
    public List<Schedule> findScheduleForStudent(FindScheduleRequest request) throws Throwable {

        Student byNameAndLastName = findByNameAndLastName(request.getName(), request.getLastName());

        List<Schedule> schedules = scheduleRepo.findByStudentGroupAndWeekNumber(byNameAndLastName.getStudentGroup(), request.getWeekNumber());

        List<LecturerDTO> lecturerDTOList = new ArrayList<>();


        schedules.stream().forEach(schedule1 -> {
            List<Subject> subjects1 = schedule1.getSubjects();
            subjects1.stream().forEach(subject -> {
                @NotNull(message = "Lista wykładowców nie może być null.") List<Lecturer> lecturer = subject.getLecturer();

                lecturer.forEach(lecturer1 -> {
                    LecturerDTO lecturerDTO = LecturerMapper.mapTo(lecturer1);
                    lecturerDTOList.add(lecturerDTO);
                });

            });

        });

//        todo Test->   W request puste imie i nazwisko.
//        todo -> Brak numeru tygodnia w bazie
        return schedules;
    }
}

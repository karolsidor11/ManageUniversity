package pl.sidor.ManageUniversity.lecturer.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.request.FindScheduleRequest
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import spock.lang.Specification

class LecturerServiceImplTest extends Specification {

    private LecturerRepo lecturerRepo = Mock(LecturerRepo.class)
    private SubjectRepo subjectRepo = Mock(SubjectRepo.class)
    private ScheduleRepo scheduleRepo = Mock(ScheduleRepo.class)

    private LecturerServiceImpl lecturerService = [lecturerRepo, subjectRepo, scheduleRepo]

    def "should find lecturer by Id"() {
        given:
        Lecturer lecturer = getLecturers()

        when:
        lecturerRepo.findById(1) >> Optional.ofNullable(lecturer)
        Lecturer actualLecturer = lecturerService.findById(1)

        then:
        actualLecturer != null
        actualLecturer == lecturer
    }

    def "should throw Exception when  lecturer ID is incorrect"() {
        given:
        Long id = 999

        when:
        lecturerRepo.findById(id) >> Optional.empty()
        lecturerService.findById(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie nie istnieje WYKŁADOWCA o podanym id.:" + id
    }

    def "should create Lecturer"() {
        given:
        Lecturer lecturer = getLecturer()

        when:
        lecturerRepo.findByEmail(lecturer.getEmail()) >> null
        lecturerRepo.save(lecturer) >> lecturer

        Lecturer actualLecturer = lecturerService.create(lecturer)

        then:
        actualLecturer != null
        actualLecturer == lecturer
    }

    def "should delete Lecturer"() {
        given:
        Long id = 1

        when:
        lecturerRepo.findById(id) >> Optional.of(getLecturer())
        lecturerService.delete(id)

        then:
        1 * lecturerRepo.deleteById(_)
    }

    def " should dont delete Lecturer- incorrect ID"() {
        given:
        Long id = 989

        when:
        lecturerRepo.findById(id) >> Optional.empty()
        lecturerRepo.deleteById(id)

        lecturerService.delete(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie nie istnieje WYKŁADOWCA o podanym id.:989"
    }

    def "should  throw Exception when lecturer ID is incorrect"() {
        given:
        Long id = 9999

        when:
        lecturerRepo.findById(id) >> Optional.empty()
        lecturerService.delete(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie nie istnieje WYKŁADOWCA o podanym id.:" + id
    }

    def "should update lecturer"() {
        given:
        Lecturer lecturer = getLecturer()

        when:
        lecturerRepo.findById(1) >> Optional.ofNullable(lecturer)
        Lecturer actualLectruer = lecturerService.findById(1)

        actualLectruer.setName("Marek")
        actualLectruer.setLastName("Nowak")
        actualLectruer.setEmail("nowak12@wp.pl")

        lecturerService.update(actualLectruer)

        then:
        actualLectruer.name == "Marek"
        actualLectruer.lastName == "Nowak"
    }

    def "test should find schedule for Lecturer"() {
        given:
        Lecturer lecturer = getLecturer()
        Subject subject = getSubject()
        Schedule schedule = getSchedule()

        when:
        lecturerRepo.findByNameAndLastName("Jan", "Nowak") >> Optional.of(lecturer)
        subjectRepo.findByLecturer(lecturer) >> Optional.of(subject)
        scheduleRepo.findBySubjects(subject) >> Arrays.asList(schedule)
        List<Schedule> actualList = lecturerService.findScheduleForLecturer(getRequest())

        then:
        actualList != null
        actualList.get(0).weekNumber == 12
    }

    private static FindScheduleRequest getRequest() {
        return FindScheduleRequest.builder()
                .name("Jan")
                .lastName("Nowak")
                .weekNumber(12)
                .build()
    }

    private static Lecturer getLecturer() {
        return Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .grade("Doctor")
                .email("nowak@wp.pl")
                .build()
    }

    private static Subject getSubject() {
        return Subject.builder()
                .id(1)
                .name("Polski")
                .lecturer(Arrays.asList(getLecturer()))
                .build()
    }

    private static Schedule getSchedule() {
        return Schedule.builder()
                .id(1)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .build()
    }

    private static Lecturer getLecturers() {
        Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .build()
    }
}

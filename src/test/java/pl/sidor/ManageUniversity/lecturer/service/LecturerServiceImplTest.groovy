package pl.sidor.ManageUniversity.lecturer.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer
import pl.sidor.ManageUniversity.request.FindScheduleRequest
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import spock.lang.Specification

class LecturerServiceImplTest extends Specification {

    private LecturerRepo lecturerRepo
    private CheckLecturer checkLecturer
    private LecturerServiceImpl lecturerService
    private SubjectRepo subjectRepo
    private ScheduleRepo scheduleRepo

    void setup() {
        lecturerRepo = Mock(LecturerRepo.class)
        subjectRepo = Mock(SubjectRepo.class)
        scheduleRepo = Mock(ScheduleRepo)
        checkLecturer = new CheckLecturer(lecturerRepo)
        lecturerService = new LecturerServiceImpl(lecturerRepo, checkLecturer, subjectRepo, scheduleRepo)
    }


    def "should find lecturer by Id"() {
        given:
        Lecturer lecturer = Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .build()

        lecturerRepo.findById(1) >> Optional.ofNullable(lecturer)

        when:
        Lecturer actualLecturer = lecturerService.findById(1)

        then:
        actualLecturer != null
        actualLecturer == lecturer
    }

    def "should throw Exception when  lecturer ID is incorrect"() {

        given:
        Long id = 999
        lecturerRepo.findById(id) >> Optional.empty()
        when:
        lecturerService.findById(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie nie istnieje WYKŁADOWCA o podanym id.:" + id
    }

    def "should create Lecturer"() {

        given:
        Lecturer lecturer = Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .build()

        lecturerRepo.save(lecturer) >> lecturer

        when:
        Lecturer actualLecturer = lecturerService.create(lecturer)

        then:
        actualLecturer != null
        actualLecturer == lecturer
    }

    def "should  throw Exception when lecturer email is not uniqe"() {
        given:
        Lecturer lecturer = Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .build()

        lecturerRepo.findByEmail(lecturer.getEmail()) >> Arrays.asList(lecturer)

        when:
        lecturerService.create(lecturer)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie istnieje WYKŁADOWCA o podanym adresie email.:jankowalski@wp.pl"

    }

    def "should delete Lecturer"() {
        given:
        Long id = 1
        lecturerRepo.findById(id) >> Optional.of(Lecturer.builder().id(1).build())

        when:
        lecturerService.delete(id)

        then:
        1 * lecturerRepo.deleteById(_)
    }

    def " should dont delete Lecturer- incorrect ID"() {
        given:
        Long id = 989

        lecturerRepo.findById(id) >> Optional.empty()
        lecturerRepo.deleteById(id)

        when:
        lecturerService.delete(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie nie istnieje WYKŁADOWCA o podanym id.:989"

    }


    def "shulud  throw Exception when lecturer ID is incorrect"() {

        given:
        Long id = 9999
        lecturerRepo.findById(id) >> Optional.empty()

        when:
        lecturerService.delete(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W bazie nie istnieje WYKŁADOWCA o podanym id.:" + id
    }

    def "should update lecturer"() {

        given:
        Lecturer lecturer = Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .build()

        lecturerRepo.findById(1) >> Optional.ofNullable(lecturer)

        when:
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

        lecturerRepo.findByNameAndLastName("Jan", "Nowak") >> Optional.of(lecturer)
        subjectRepo.findByLecturer(lecturer) >> Optional.of(subject)
        scheduleRepo.findBySubjects(subject) >> Arrays.asList(schedule)

        when:
        List<Schedule> actualList = lecturerService.findScheduleForLecturer(getRequest())

        then:
        actualList != null
        actualList.get(0).weekNumber == 12
    }

    private FindScheduleRequest getRequest() {
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

    def "test  should throw exception"() {
        given:
        Lecturer lecturer = getLecturer()
        Subject subject = getSubject()

        lecturerRepo.findByNameAndLastName("Jan", "Nowak") >> Optional.of(lecturer)
        subjectRepo.findByLecturer(lecturer) >> Optional.of(subject)

        scheduleRepo.findBySubjects(subject) >> Collections.emptyList()

        when:
        lecturerService.findScheduleForLecturer(getRequest())

        then:
        UniversityException exception = thrown()
        exception.message == "Wystąpił nieoczekiwany błąd systemu. Nie znaleziono rozkładu dla podanych parametrów :  Jan Nowak 12"

    }
}

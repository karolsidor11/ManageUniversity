package pl.sidor.ManageUniversity.lecturer.service


import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse
import pl.sidor.ManageUniversity.lecturer.utils.LecturerUtils
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
        Lecturer lecturer = LecturerUtils.getLecturers()

        when:
        lecturerRepo.findById(1) >> Optional.ofNullable(lecturer)
        Lecturer actualLecturer = lecturerService.findById(1).getLecturer()

        then:
        actualLecturer != null
        actualLecturer == lecturer
    }

    def "should throw Exception when  lecturer ID is incorrect"() {
        given:
        Long id = 999

        when:
        lecturerRepo.findById(id) >> Optional.empty()
        LecturerResponse lecturerResponse = lecturerService.findById(id)

        then:
        lecturerResponse.error != null
    }

    def "should create Lecturer"() {
        given:
        Lecturer lecturer = LecturerUtils.getLecturer()

        when:
        lecturerRepo.findByEmail(lecturer.getEmail()) >> null
        lecturerRepo.save(lecturer) >> lecturer

        Lecturer actualLecturer = lecturerService.create(lecturer).getLecturer()

        then:
        actualLecturer != null
        actualLecturer == lecturer
    }

    def "should delete Lecturer"() {
        given:
        Long id = 1

        when:
        lecturerRepo.findById(id) >> Optional.of(LecturerUtils.getLecturer())
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

        LecturerResponse lecturerResponse = lecturerService.delete(id)

        then:
        lecturerResponse.error != null
    }

    def "should  throw Exception when lecturer ID is incorrect"() {
        given:
        Long id = 9999

        when:
        lecturerRepo.findById(id) >> Optional.empty()
        LecturerResponse lecturerResponse = lecturerService.delete(id)

        then:
        lecturerResponse.error != null
    }

    def "should update lecturer"() {
        given:
        Lecturer lecturer = LecturerUtils.getLecturer()

        when:
        lecturerRepo.findById(1) >> Optional.ofNullable(lecturer)
        Lecturer actualLectruer = lecturerService.findById(1).getLecturer()

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
        Lecturer lecturer = LecturerUtils.getLecturer()
        Subject subject = LecturerUtils.getSubject()
        Schedule schedule = LecturerUtils.getSchedule()

        when:
        lecturerRepo.findByNameAndLastName("Jan", "Nowak") >> Optional.of(lecturer)
        subjectRepo.findByLecturer(lecturer) >> Optional.of(subject)
        scheduleRepo.findBySubjects(subject) >> Arrays.asList(schedule)
        List<Schedule> actualList = lecturerService.findScheduleForLecturer(LecturerUtils.getRequest())

        then:
        actualList != null
        actualList.get(0).weekNumber == 12
    }
}

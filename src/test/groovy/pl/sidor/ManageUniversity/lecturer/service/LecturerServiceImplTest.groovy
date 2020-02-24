package pl.sidor.ManageUniversity.lecturer.service

import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse
import pl.sidor.ManageUniversity.lecturer.utils.LecturerUtils
import spock.lang.Specification

class LecturerServiceImplTest extends Specification {

    private LecturerRepo lecturerRepo = Mock(LecturerRepo.class)
    private LecturerServiceImpl lecturerService = [lecturerRepo]

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
        lecturerRepo.save(_ as Lecturer)>>lecturer
        Lecturer actualLecturer = lecturerService.findById(1).getLecturer()

        actualLecturer.setName("Marek")
        actualLecturer.setLastName("Nowak")
        actualLecturer.setEmail("nowak12@wp.pl")

        lecturerService.update(actualLecturer)

        then:
        actualLecturer.name == "Marek"
        actualLecturer.lastName == "Nowak"
    }
}

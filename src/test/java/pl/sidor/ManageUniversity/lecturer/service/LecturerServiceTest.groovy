package pl.sidor.ManageUniversity.lecturer.service

import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import spock.lang.Specification

class LecturerServiceTest extends Specification {

    private LecturerRepo lecturerRepo
    private LecturerService lecturerService

    void setup() {
        lecturerRepo = Mock(LecturerRepo.class)
        lecturerService = new LecturerService(lecturerRepo)
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

    def "should delete Lecturer"() {
        given:
        long id = 1
        lecturerRepo.deleteById(id)

        when:
        lecturerService.delete(id)

        then:
        1 * lecturerRepo.deleteById(_)
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
        actualLectruer.name=="Marek"

    }
}

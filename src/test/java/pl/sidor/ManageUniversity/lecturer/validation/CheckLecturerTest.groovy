package validation

import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer
import spock.lang.Specification

class CheckLecturerTest extends Specification {

    private LecturerRepo lecturerRepo
    private CheckLecturer checkLecturer

    void setup() {
        lecturerRepo = Mock(LecturerRepo.class)
        checkLecturer = new CheckLecturer(lecturerRepo)
    }

    def "should true if Lecturer have unige emial"() {

        given:
        Lecturer lecturer = Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak99@wp.pl")
                .build()

        lecturerRepo.findByEmail("nowak@wp.pl") >> Collections.emptyList()

        when:

        boolean result = checkLecturer.test(lecturer)

        then:
        result == true
    }


    def " should false when Lecturer emial is not uniqe"() {

        given:
        Lecturer lecturer = Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak99@wp.pl")
                .build()

        lecturerRepo.findByEmail("nowak99@wp.pl") >> Arrays.asList(lecturer)
        when:
        boolean result = checkLecturer.test(lecturer)

        then:
        result == false

    }
}

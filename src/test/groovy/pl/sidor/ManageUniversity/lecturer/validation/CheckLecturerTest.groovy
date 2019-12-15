package pl.sidor.ManageUniversity.lecturer.validation

import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import spock.lang.Specification

class CheckLecturerTest extends Specification {

    private LecturerRepo lecturerRepo=Mock(LecturerRepo.class)
    private CheckLecturer checkLecturer= [lecturerRepo]

    def "should true if Lecturer have unige emial"() {
        given:
        Lecturer lecturer = getLecturer()

        when:
        lecturerRepo.findByEmail("nowak@wp.pl") >> Collections.emptyList()
        boolean result = checkLecturer.test(lecturer)

        then:
        result
    }

    def " should false when Lecturer emial is not uniqe"() {
        given:
        Lecturer lecturer = getLecturer()

        when:
        lecturerRepo.findByEmail("nowak99@wp.pl") >> Arrays.asList(lecturer)
        boolean result = checkLecturer.test(lecturer)

        then:
        !result
    }

    private static Lecturer getLecturer() {
        return Lecturer.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak99@wp.pl")
                .build()
    }
}

package pl.sidor.ManageUniversity.mapper

import pl.sidor.ManageUniversity.dto.LecturerDTO
import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import spock.lang.Specification

class LecturerMapperTest extends Specification {

    def "test should convert Lecturer"() {
        given:
        Lecturer actualLecturer = getLecturer()

        when:
        LecturerDTO lecturerDTO = LecturerMapper.mapTo(actualLecturer)

        then:
        lecturerDTO != null
        lecturerDTO.name == "Jan"
        lecturerDTO.lastName == "Nowak"
    }

    private static Lecturer getLecturer() {
        Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .email("jan@wp.pl")
                .phoneNumber(500500500)
                .build()
    }
}

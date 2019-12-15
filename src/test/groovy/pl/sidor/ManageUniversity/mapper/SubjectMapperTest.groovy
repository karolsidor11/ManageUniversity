package pl.sidor.ManageUniversity.mapper

import pl.sidor.ManageUniversity.dto.SubjectDTO
import pl.sidor.ManageUniversity.schedule.model.Subject
import spock.lang.Specification

class SubjectMapperTest extends Specification {


    def "should convert to SubjectDto"() {
        given:
        Subject subject = getSubject()

        when:
        SubjectDTO subjectDTO = SubjectMapper.mapTo(subject)

        then:
        subjectDTO != null
        subjectDTO.name == "Polski"
        subjectDTO.roomNumber == 22
    }

    private static Subject getSubject() {
        return Subject.builder()
                .id(1L)
                .name("Polski")
                .roomNumber(22)
                .lecturer(Collections.emptyList())
                .build()
    }
}

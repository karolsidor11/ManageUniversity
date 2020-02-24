package pl.sidor.ManageUniversity.schedule.validator

import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import spock.lang.Specification

import java.time.LocalTime

class SubjectValidatorTest extends Specification {

    private SubjectRepo subjectRepo = Mock(SubjectRepo.class)
    private SubjectValidator subjectValidator = [subjectRepo]

    def "should true if timeStart is good"() {
        given:
        Subject subject = getSubject()

        when:
        subjectRepo.findByStartTimeAndEndTime(_ as LocalTime, _ as LocalTime) >> Optional.empty()
        boolean result = subjectValidator.test(subject)

        then:
        result
    }

    def "should false when timeStart is bad"() {
        given:
        Subject subject = getSubject()

        when:
        subjectRepo.findByStartTimeAndEndTime(_ as LocalTime, _ as LocalTime) >> Optional.of(subject)
        boolean result = subjectValidator.test(subject)

        then:
        !result
    }

    private static Subject getSubject() {
        return Subject.builder()
                .id(1)
                .name("Polski")
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(15, 00))
                .build()
    }
}

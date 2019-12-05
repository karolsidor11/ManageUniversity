package pl.sidor.ManageUniversity.schedule.validator

import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator
import spock.lang.Specification

import java.time.LocalTime

class SubjectValidatorTest extends Specification {

    private SubjectRepo subjectRepo
    private SubjectValidator subjectValidator

    void setup() {

        subjectRepo = Mock(SubjectRepo.class)
        subjectValidator = new SubjectValidator(subjectRepo)
    }

    def "should true if timeStart is good"() {

        given:
        LocalTime start = LocalTime.of(12, 00)
        LocalTime end = LocalTime.of(14, 00)

        Subject subject = Subject.builder()
                .id(1)
                .name("Polski")
                .startTime(LocalTime.of(11, 00))
                .endTime(LocalTime.of(11, 30))
                .build()

        subjectRepo.findByStartTimeAndEndTime(start, end) >> Optional.empty()

        when:
        boolean result = subjectValidator.test(subject)

        then:
        result
    }

    def "should false when timeStart is bad"() {

        given:

        LocalTime start = LocalTime.of(13, 00)
        LocalTime end = LocalTime.of(15, 00)

        Subject subject = Subject.builder()
                .id(1)
                .name("Polski")
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(15, 00))
                .build()

        subjectRepo.findByStartTimeAndEndTime(start, end) >> Optional.of(subject)

        when:
        boolean result = subjectValidator.test(subject)

        then:
        !result
    }
}

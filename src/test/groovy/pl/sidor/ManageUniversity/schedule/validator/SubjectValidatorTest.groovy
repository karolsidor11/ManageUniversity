package pl.sidor.ManageUniversity.schedule.validator

import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import pl.sidor.ManageUniversity.schedule.utils.SubejctUtils
import spock.lang.Specification

import java.time.LocalTime

class SubjectValidatorTest extends Specification {

    private SubjectRepo subjectRepo = Mock(SubjectRepo.class)
    private SubjectValidator subjectValidator = [subjectRepo]

    def "should true if timeStart is good"() {
        given:
        Subject subject = SubejctUtils.getSubject()

        when:
        subjectRepo.findByStartTimeAndEndTime(_ as LocalTime, _ as LocalTime) >> Collections.emptyList()
        boolean result = subjectValidator.test(subject)

        then:
        result
    }

    def "should false when timeStart is bad"() {
        given:
        Subject subject = SubejctUtils.getSubject()

        when:
        subjectRepo.findByStartTimeAndEndTime(_ as LocalTime, _ as LocalTime) >> Arrays.asList(subject)
        boolean result = subjectValidator.test(subject)

        then:
        !result
    }
}

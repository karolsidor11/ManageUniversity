package pl.sidor.ManageUniversity.schedule.service


import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import pl.sidor.ManageUniversity.schedule.response.SubjectResponse
import pl.sidor.ManageUniversity.schedule.utils.SubejctUtils
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator
import spock.lang.Specification


class SubjectServiceImplTest extends Specification {

    private SubjectRepo subjectRepo = Mock(SubjectRepo.class)
    private LecturerRepo lecturerRepo = Mock(LecturerRepo.class)
    private SubjectValidator subjectValidator = Mock(SubjectValidator.class)

    private SubjectServiceImpl service = [subjectRepo, subjectValidator, lecturerRepo]

    def "should  find subject by id"() {
        given:
        Subject subject = SubejctUtils.getSubject()

        when:
        subjectRepo.findById(1) >> Optional.of(subject)
        Subject actualSubejct = service.getById(1).getSubject()

        then:
        actualSubejct != null
        actualSubejct == subject
    }

    def "should throw Exception"() {
        given:
        Long id = 999

        when:
        subjectRepo.findById(id) >> Optional.empty()
        SubjectResponse subjectResponse = service.getById(id)

        then:
        subjectResponse.error != null
    }

    def "should save Subject"() {
        given:
        Subject subject = SubejctUtils.getSubject()

        when:
        subjectValidator.test(subject) >> true
        subjectRepo.save(subject) >> subject
        Subject actualSubject = service.save(subject).getSubject()

        then:
        actualSubject != null
        actualSubject == subject
    }

    def "should  throw save Subject"() {
        given:
        Subject subject = SubejctUtils.getAllSubject()

        when:
        subjectRepo.findByStartTimeAndEndTime(_, _) >> false
        subjectRepo.save(_) >> subject
        SubjectResponse subjectResponse = service.save(subject)

        then:
        subjectResponse.error != null
    }

    def "should delete subject by ID"() {
        given:
        Long id = 1

        when:
        subjectRepo.findById(id) >> Optional.of(SubejctUtils.getSubject())
        subjectRepo.deleteById(id)
        service.delete(id)

        then:
        2 * subjectRepo.deleteById(id)
    }
}

package pl.sidor.ManageUniversity.schedule.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator
import spock.lang.Specification

import java.time.LocalTime

class SubjectServiceImplTest extends Specification {

    private SubjectRepo subjectRepo = Mock(SubjectRepo.class)
    private LecturerRepo lecturerRepo = Mock(LecturerRepo.class)
    private SubjectValidator subjectValidator = Mock(SubjectValidator.class)

    private SubjectServiceImpl service = [subjectRepo, subjectValidator, lecturerRepo]

    def "should  find subject by id"() {
        given:
        Subject subject = getSubject()

        when:
        subjectRepo.findById(1) >> Optional.of(subject)
        Subject actualSubejct = service.getById(1)

        then:
        actualSubejct != null
        actualSubejct == subject
    }

    def "should throw Exception"() {
        given:
        Long id = 999

        when:
        subjectRepo.findById(id) >> Optional.empty()
        service.getById(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_BRAK_PRZEDMIOTU:" + id
    }

    def "should save Subject"() {
        given:
        Subject subject = getSubject()

        when:
        subjectValidator.test(subject) >> true
        subjectRepo.save(subject) >> subject
        Subject actualSubject = service.save(subject)

        then:
        actualSubject != null
        actualSubject == subject
    }

    def "should  throw save Subject"() {
        given:
        Subject subject = getAllSubject()

        when:
        subjectValidator.test(subject) >> false
        service.save(subject)

        then:
        thrown(UniversityException.class)
    }

    def "should delete subject by ID"() {
        given:
        Long id = 1

        when:
        subjectRepo.findById(id) >> Optional.of(getSubject())
        subjectRepo.deleteById(id)
        service.delete(id)

        then:
        2 * subjectRepo.deleteById(id)
    }

    private static Subject getSubject() {
        Subject.builder()
                .id(1)
                .name("Polski")
                .lecturer(Arrays.asList(Lecturer.builder()
                        .id(1)
                        .name("Karol")
                        .lastName("Sidor")
                        .build()))
                .build()
    }

    private static Subject getAllSubject() {
        Subject.builder()
                .id(1)
                .name("Polski")
                .lecturer(Arrays.asList(Lecturer.builder()
                        .id(1)
                        .name("Karol")
                        .lastName("Sidor")
                        .build()))
                .endTime(LocalTime.of(12, 00))
                .endTime(LocalTime.of(13, 00))
                .build()
    }
}

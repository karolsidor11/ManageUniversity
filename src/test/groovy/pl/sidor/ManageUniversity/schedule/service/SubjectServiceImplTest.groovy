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

    private SubjectRepo subjectRepo
    private SubjectService service
    private SubjectValidator subjectValidator
    private LecturerRepo lecturerRepo

    void setup() {
        subjectRepo = Mock(SubjectRepo.class)
        lecturerRepo=Mock(LecturerRepo.class)
        subjectValidator = Mock(SubjectValidator.class)
        service = new SubjectServiceImpl(subjectRepo, subjectValidator,lecturerRepo)
    }

    def "should  find subject by id"() {

        given:
        Subject subject = Subject.builder()
                .id(1)
                .name("Polski")
                .lecturer(Arrays.asList(Lecturer.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .build()))
                .build()

        subjectRepo.findById(1) >> Optional.of(subject)

        when:
        Subject actualSubejct = service.getById(1)

        then:
        actualSubejct != null
        actualSubejct == subject
    }


    def "should throw Exception"() {

        given:
        Long id = 999
        subjectRepo.findById(id) >> Optional.empty()

        when:
        service.getById(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_BRAK_PRZEDMIOTU:" + id
    }

    def "should save Subject"() {

        given:
        LocalTime start = LocalTime.of(12, 00)
        LocalTime end = LocalTime.of(13, 00)
        Subject subject = Subject.builder()
                .id(1)
                .name("Polski")
                .lecturer(Arrays.asList(Lecturer.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .build()))
                .endTime(start)
                .endTime(end)
                .build()

        subjectValidator.test(subject) >> true
        subjectRepo.save(subject) >> subject

        when:

        Subject actualSubject = service.save(subject)

        then:
        actualSubject != null
        actualSubject == subject
    }

    def "should  throw save Subject"() {

        given:
        LocalTime start = LocalTime.of(12, 00)
        LocalTime end = LocalTime.of(13, 00)
        Subject subject = Subject.builder()
                .id(1)
                .name("Polski")
                .lecturer(Arrays.asList(Lecturer.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .build()))
                .endTime(start)
                .endTime(end)
                .build()

        subjectValidator.test(subject) >> false

        when:

        service.save(subject)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_ISTNIEJE_PRZEDMIOT_W_CZASIE:" + end.toString()
    }

    def "should delete subject by ID"() {
        given:
        long id = 1
        subjectRepo.findById(id) >> Optional.of(Subject.builder().id(id).build())
        subjectRepo.deleteById(id)

        when:
        service.delete(id)

        then:
        1*subjectRepo.deleteById(id)

    }
}

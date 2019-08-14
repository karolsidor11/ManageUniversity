package pl.sidor.ManageUniversity.schedule.service

import pl.sidor.ManageUniversity.lecturer.model.Lecturer
import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import spock.lang.Specification

class SubjectServiceImplTest extends Specification {

    private SubjectRepo subjectRepo
    private SubjectService service

    void setup() {
        subjectRepo = Mock(SubjectRepo.class)
        service = new SubjectServiceImpl(subjectRepo)
    }

    def "should   find subject by id"() {

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
    }


    def "should throw Exception"() {

        given:
        subjectRepo.findById(1) >> Optional.empty()

        when:
        service.getById(1)

        then:
        thrown(NoSuchElementException.class)
    }

    def "should save Subject"() {

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

        subjectRepo.save(subject) >> subject
        when:

        Subject actualSubject = service.save(subject)

        then:
        actualSubject != null
    }

    def "should  throw save Subject"() {

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

        subjectRepo.save(subject) >> Exception
        when:

        service.save(subject)

        then:
        thrown(Exception.class)
    }
}

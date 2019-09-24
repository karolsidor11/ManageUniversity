package pl.sidor.ManageUniversity.evaluation

import pl.sidor.ManageUniversity.evaluation.model.RatingSet
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo
import pl.sidor.ManageUniversity.evaluation.service.RatingSetServiceImpl
import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.schedule.model.Subject
import spock.lang.Specification

class RatingSetServiceTest extends Specification {

    private RatingRepo ratingRepo
    private RatingSetServiceImpl service

    void setup() {
        ratingRepo = Mock(RatingRepo.class)
        service = new RatingSetServiceImpl(ratingRepo)
    }

    def "should find by ID"() {

        given:
        Long id = 1
        ratingRepo.findById(id) >> Optional.of(RatingSet.builder().id(1).build())

        when:
        def result = service.findById(id)

        then:
        result != null
        result.id == 1
    }

    def "should throw exception when find by ID"() {

        given:
        Long id = 999

        ratingRepo.findById(id) >> Optional.empty()

        when:
        service.findById(id)

        then:
        thrown(UniversityException)
    }

    def "should delete by ID"() {

        given:
        Long id = 1
        ratingRepo.findById(id) >> Optional.of(RatingSet.builder().id(id).build())
        ratingRepo.deleteById(id)

        when:
        def result = service.delete(id)

        then:
        result == true
    }

    def "should throw exception when delete by ID"() {
        given:
        Long id = 9999
        ratingRepo.findById(id) >> Optional.empty()
        ratingRepo.deleteById(id)

        when:
        service.delete(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_BRAK_ZESTAW_OCEN:" + id
    }

    def " should create RatingSet"() {
        given:
        RatingSet ratingSet = RatingSet.builder()
                .id(1)
                .subject(Subject.builder().id(1).build())
                .build()

        ratingRepo.save(ratingSet) >> ratingSet

        when:

        def result = service.create(ratingSet)

        then:
        result != null
        result.id == ratingSet.id
        result == ratingSet
    }

    def "should throw exception when RatingSet is Empty"() {

        given:

        RatingSet ratingSet = null
        ratingRepo.save(ratingSet) >> ratingSet

        when:
         service.create(ratingSet)

        then:
        thrown(Exception)
    }

    def"should update RatingSet"(){
        given:

        RatingSet actual=RatingSet.builder()
                .id(1)
                .subject(Subject.builder()
                    .name("Matematyka")
                    .build())
                .build()
        RatingSet update=RatingSet.builder().id(1)
                .subject(Subject.builder()
                    .name("Chemia")
                    .build())
                .build()

        ratingRepo.findById(1)>>Optional.of(actual)
        ratingRepo.save(update)>>update

        when:
       def result= service.update(update)

         then:
         result==update
    }
}

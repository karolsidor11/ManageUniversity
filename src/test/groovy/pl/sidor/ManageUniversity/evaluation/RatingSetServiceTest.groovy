package pl.sidor.ManageUniversity.evaluation

import pl.sidor.ManageUniversity.evaluation.model.RatingSet
import pl.sidor.ManageUniversity.evaluation.ratingset.RatingSetServiceImpl
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo
import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.schedule.model.Subject
import spock.lang.Specification

class RatingSetServiceTest extends Specification {

    private RatingRepo ratingRepo = Mock(RatingRepo.class)
    private RatingSetServiceImpl service = [ratingRepo]

    def "should find by ID"() {
        given:
        Long id = 1

        when:
        ratingRepo.findById(id) >> Optional.of(getRatingSet())
        def result = service.findById(id)

        then:
        result != null
        result.id == 1
    }

    def "should throw exception when find by ID"() {
        given:
        Long id = 999

        when:
        ratingRepo.findById(id) >> Optional.empty()
        service.findById(id)

        then:
        thrown(UniversityException)
    }

    def "should delete by ID"() {
        given:
        Long id = 1

        when:
        ratingRepo.findById(id) >> Optional.of(getRatingSet())
        ratingRepo.deleteById(id)
        def result = service.delete(id)

        then:
        result
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
        RatingSet ratingSet = getRatingSet()

        when:
        ratingRepo.save(ratingSet) >> ratingSet
        def result = service.create(ratingSet)

        then:
        result != null
        result.id == ratingSet.id
        result == ratingSet
    }

    def "should throw exception when RatingSet is Empty"() {
        given:
        RatingSet ratingSet = null
        when:
        ratingRepo.save(ratingSet) >> ratingSet
        service.create(ratingSet)

        then:
        thrown(UniversityException)
    }

    def "should update RatingSet"() {
        given:
        RatingSet actual = getRatingSet()
        RatingSet update = getModifyRatingSet()

        when:
        ratingRepo.findById(1) >> Optional.of(actual)
        ratingRepo.save(update) >> update
        def result = service.update(update)

        then:
        result == update
    }

    private static RatingSet getModifyRatingSet() {
        RatingSet.builder().id(1)
                .subject(Subject.builder()
                        .name("Chemia")
                        .build())
                .build()
    }

    private static RatingSet getRatingSet() {
        RatingSet.builder()
                .id(1)
                .subject(Subject.builder()
                        .name("Matematyka")
                        .build())
                .build()
    }
}

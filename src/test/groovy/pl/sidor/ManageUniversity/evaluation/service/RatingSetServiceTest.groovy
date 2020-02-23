package pl.sidor.ManageUniversity.evaluation.service

import pl.sidor.ManageUniversity.evaluation.model.RatingSet
import pl.sidor.ManageUniversity.evaluation.ratingset.RatingSetServiceImpl
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo
import pl.sidor.ManageUniversity.evaluation.utils.RatingSetUtils
import spock.lang.Specification

class RatingSetServiceTest extends Specification {

    private RatingRepo ratingRepo = Mock(RatingRepo.class)
    private RatingSetServiceImpl service = [ratingRepo]

    def "should find by ID"() {
        given:
        Long id = 1

        when:
        ratingRepo.findById(id) >> Optional.of(RatingSetUtils.getRatingSet())
        def result = service.findById(id)

        then:
        result != null
        result.ratingSet.id == 1
    }

    def "should throw exception when find by ID"() {
        given:
        Long id = 999

        when:
        ratingRepo.findById(id) >> Optional.empty()
        def result = service.findById(id)

        then:
        result.error != null
    }

    def "should delete by ID"() {
        given:
        Long id = 1

        when:
        ratingRepo.findById(id) >> Optional.of(RatingSetUtils.getRatingSet())
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
        def result = service.delete(id)

        then:
        result.error != null
    }

    def " should create RatingSet"() {
        given:
        RatingSet ratingSet = RatingSetUtils.getRatingSet()

        when:
        ratingRepo.save(ratingSet) >> ratingSet
        def result = service.create(ratingSet)

        then:
        result != null
        result.ratingSet.id == ratingSet.id
        result.ratingSet.equals(ratingSet)
    }

    def "should throw exception when RatingSet is Empty"() {
        given:
        RatingSet ratingSet = null
        when:
        ratingRepo.save(ratingSet) >> ratingSet
        def result = service.create(ratingSet)

        then:
        result.error != null
    }

    def "should update RatingSet"() {
        given:
        RatingSet actual = RatingSetUtils.getRatingSet()
        RatingSet update = RatingSetUtils.getModifyRatingSet()

        when:
        ratingRepo.findById(1) >> Optional.of(actual)
        ratingRepo.save(update) >> update
        def result = service.update(update)

        then:
        result.ratingSet == update
    }
}

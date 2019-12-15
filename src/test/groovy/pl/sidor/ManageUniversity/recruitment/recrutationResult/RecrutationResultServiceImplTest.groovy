package pl.sidor.ManageUniversity.recruitment.recrutationResult

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo
import pl.sidor.ManageUniversity.recruitment.service.recrutationResult.RecrutationResultServiceImpl
import spock.lang.Specification

class RecrutationResultServiceImplTest extends Specification {

    private RecrutationResultRepo recrutationResultRepo = Mock(RecrutationResultRepo.class)
    private RecrutationResultServiceImpl resultService = [recrutationResultRepo]

    def "should find recrutation result"() {
        given:
        def recrutation = getRecrutation()

        when:
        recrutationResultRepo.findByNameAndLastName("Karol", "Sidor") >> recrutation
        def result = resultService.checkRecrutationResult(recrutation.getName(), recrutation.getLastName())

        then:
        result != null
        result.getName() == "Karol"
        result.getLastName() == "Sidor"
        result.getIsAccept() == AcceptedInCollage.ACCEPTED
    }

    def "should throw exception"() {

        given:
        RecrutationResult result = null

        when:
        recrutationResultRepo.findByNameAndLastName("Karol", "Sidor") >> result
        resultService.checkRecrutationResult("Karol", "Sidor")

        then:
        thrown(UniversityException.class)
    }

    private static RecrutationResult getRecrutation() {
        RecrutationResult.builder()
                .name("Karol")
                .lastName("Sidor")
                .isAccept(AcceptedInCollage.ACCEPTED)
                .build()
    }
}

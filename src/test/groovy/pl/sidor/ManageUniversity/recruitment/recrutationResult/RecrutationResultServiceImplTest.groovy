package pl.sidor.ManageUniversity.recruitment.recrutationResult

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
        recrutationResultRepo.findByNameAndLastName("Karol", "Sidor") >> Optional.of(recrutation)
        def result = resultService.checkRecrutationResult(recrutation.getName(), recrutation.getLastName())

        then:
        result != null
        result.getRecrutationResult().getName() == "Karol"
        result.getRecrutationResult().getLastName() == "Sidor"
        result.getRecrutationResult().getIsAccept() == AcceptedInCollage.ACCEPTED
    }

    def "should throw exception"() {
        when:
        recrutationResultRepo.findByNameAndLastName("Karol", "Sidor") >> Optional.empty()
        def result = resultService.checkRecrutationResult("Karol", "Sidor")

        then:
        result.getError()!=null
    }

    private static RecrutationResult getRecrutation() {
        RecrutationResult.builder()
                .name("Karol")
                .lastName("Sidor")
                .isAccept(AcceptedInCollage.ACCEPTED)
                .build()
    }
}

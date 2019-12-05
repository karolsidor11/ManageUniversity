package pl.sidor.ManageUniversity.recruitment.service.recrutationResult

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo
import spock.lang.Specification

class RecrutationResultServiceImplTest extends Specification {

    private RecrutationResultService resultService
    private RecrutationResultRepo recrutationResultRepo

    void setup() {
        recrutationResultRepo = Mock(RecrutationResultRepo.class)
        resultService = new RecrutationResultServiceImpl(recrutationResultRepo)
    }


    def "should find recrutation result"() {

        given:
        def recrutation = RecrutationResult.builder()
                .name("Karol")
                .lastName("Sidor")
                .isAccept(AcceptedInCollage.ACCEPTED)
                .build()

        recrutationResultRepo.findByNameAndLastName("Karol", "Sidor") >> recrutation

        when:
        def result = resultService.checkRecrutationResult(recrutation.getName(), recrutation.getLastName())

        then:
        result != null
        result.getName().equals("Karol")
        result.getLastName().equals("Sidor")
        result.getIsAccept().equals(AcceptedInCollage.ACCEPTED)
    }

    def "should throw exception"() {

        given:
        RecrutationResult result = null

        recrutationResultRepo.findByNameAndLastName("Karol", "Sidor")>>result

        when:
        resultService.checkRecrutationResult("Karol", "Sidor")

        then:
        thrown(UniversityException.class)
    }
}

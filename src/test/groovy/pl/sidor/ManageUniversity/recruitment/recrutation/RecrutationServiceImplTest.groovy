package pl.sidor.ManageUniversity.recruitment.recrutation

import pl.sidor.ManageUniversity.recruitment.model.MaturaResults
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationServiceImpl
import spock.lang.Specification

class RecrutationServiceImplTest extends Specification {

    private RecrutationServiceImpl service = new RecrutationServiceImpl()

    def "should return recrutation result"() {
        given:
        MaturaResults maturaResults = getMaturaResults()

        when:
        def result = service.process(maturaResults)

        then:
        result != null
        result > 320
        result == 13850
    }

    def "should throw exception when data is  null"() {
        given:
        MaturaResults maturaResults = null

        when:
        def result = service.process(maturaResults)

        then:
        result==0.0
    }

    private static MaturaResults getMaturaResults() {
        MaturaResults.builder()
                .mathResult(120)
                .languageResult(90)
                .polishResult(100)
                .build()
    }
}



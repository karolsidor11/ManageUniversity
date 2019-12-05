package pl.sidor.ManageUniversity.recruitment.service.recrutationResult

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.recruitment.model.MaturaResults
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationServiceImpl
import spock.lang.Specification

class RecrutationServiceImplTest extends Specification {

    private RecrutationServiceImpl service

    void setup() {
        service = new RecrutationServiceImpl();
    }

    def "should return recrutation result"() {

        given:
        MaturaResults maturaResults = MaturaResults.builder()
                .mathResult(120)
                .languageResult(90)
                .polishResult(100)
                .build()

        when:
        def result = service.process(maturaResults)

        then:
        result!=null
        result>320
        result==13850
    }

    def"sholud  throw exception when data is  null"(){
        given:
        MaturaResults maturaResults=null

        when:
        service.process(maturaResults)

        then:
        thrown(UniversityException.class)
    }
}



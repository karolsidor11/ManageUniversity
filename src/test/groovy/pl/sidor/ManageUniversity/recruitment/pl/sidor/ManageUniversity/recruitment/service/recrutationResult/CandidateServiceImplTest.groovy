package pl.sidor.ManageUniversity.recruitment.service.recrutationResult

import configuration.data.TestCandidateData
import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.recruitment.model.Candidate
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateService
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateServiceImpl
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationService
import pl.sidor.ManageUniversity.recruitment.service.recrutationResult.RecrutationResultService
import spock.lang.Specification

class CandidateServiceImplTest extends Specification {

    private CandidateRepo candidateRepo
    private CandidateService candidateService
    private  RecrutationService recrutationService
    private  RecrutationResultRepo resultService

    void setup() {
        candidateRepo = Mock(CandidateRepo.class)
        recrutationService=Mock(RecrutationService.class)
        resultService=Mock(RecrutationResultRepo.class)
        candidateService = new CandidateServiceImpl(candidateRepo,recrutationService, resultService)
    }

    def "should find Candidate by ID"() {

        given:
        Long id = 1L

        when:
        candidateRepo.findById(id) >> Optional.of(TestCandidateData.prepareCandidate())
        def candidate = candidateService.findByID(id)

        then:
        candidate != null
        candidate.name.equals("Karol")
        candidate.lastName.equals("Sidor")
        candidate.email.equals("karolsidor11@wp.pl")

    }

    def "should throw exception when id is incorrect"() {

        given:
        Long id = -9

        when:
        candidateRepo.findById(id) >> Optional.empty()
        candidateService.findByID(id)

        then:
        def exception = thrown(UniversityException)
        exception.message.equals("W bazie  nie istnieje kandydat na studia o podanym identyfikatorze. -9")
    }

    def "should delete Candidate by ID"() {

        given:
        Long id = 1L

        when:
        candidateRepo.findById(id) >> Optional.of(TestCandidateData.prepareCandidate())
        candidateService.delete(id)

        then:
        1 * candidateRepo.deleteById(id)
    }

    def "should  throw exception when delete  by ID"() {

        given:
        Long id = 99999

        when:
        candidateRepo.findById(id) >> Optional.empty()
        candidateService.delete(id)

        then:
        def exception = thrown(UniversityException.class)
        exception.message.equals("W bazie  nie istnieje kandydat na studia o podanym identyfikatorze. 99999")
    }


    def "should  create Candidate"() {

        given:
        Candidate candidate = TestCandidateData.prepareCandidate()

        when:
        candidateRepo.save(candidate) >> candidate
        def firstCandidate = candidateService.create(candidate)

        then:
        firstCandidate != null
        firstCandidate.name.equals("Karol")
        firstCandidate.lastName.equals("Sidor")
        firstCandidate.email.equals("karolsidor11@wp.pl")
    }

    def "sholud  throw ecxeption when create Candidate"() {

        given:
        Candidate candidate = null

        when:
        candidateService.create(candidate)

        then:
        def exceprion= thrown(UniversityException.class)
        exceprion.message.equals("Przekazywany obiekt jest pusty.:!!!")
    }
}

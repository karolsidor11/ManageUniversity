package pl.sidor.ManageUniversity.recruitment.candidate


import pl.sidor.ManageUniversity.recruitment.model.Candidate
import pl.sidor.ManageUniversity.recruitment.model.MaturaResults
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateServiceImpl
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationService
import pl.sidor.ManageUniversity.utils.TestCandidateData
import spock.lang.Specification

class CandidateServiceImplTest extends Specification {

    private CandidateRepo candidateRepo = Mock(CandidateRepo.class)
    private RecrutationService recrutationService = Mock(RecrutationService.class)
    private RecrutationResultRepo resultService = Mock(RecrutationResultRepo.class)

    private CandidateServiceImpl candidateService = [candidateRepo, recrutationService, resultService]

    def "should find Candidate by ID"() {
        given:
        Long id = 1L

        when:
        candidateRepo.findById(id) >> Optional.of(TestCandidateData.prepareCandidate())
        def candidate = candidateService.findByID(id)

        then:
        candidate != null
        candidate.getCandidate().name == "Karol"
        candidate.getCandidate().lastName == "Sidor"
        candidate.getCandidate().email == "karolsidor11@wp.pl"
    }

    def "should throw exception when id is incorrect"() {
        given:
        Long id = -9

        when:
        candidateRepo.findById(id) >> Optional.empty()
        def result = candidateService.findByID(id)

        then:
        result.error != null
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
        def result = candidateService.delete(id)

        then:
        result.error != null
    }

    def "should  create Candidate"() {
        given:
        Candidate candidate = TestCandidateData.prepareCandidate()

        when:
        candidateRepo.save(candidate) >> candidate
        recrutationService.process(_ as MaturaResults) >> 1000
        def firstCandidate = candidateService.create(candidate)

        then:
        firstCandidate != null
        firstCandidate.getCandidate().name == "Karol"
        firstCandidate.getCandidate().lastName == "Sidor"
        firstCandidate.getCandidate().email == "karolsidor11@wp.pl"
    }

    def "should throw exception when create Candidate"() {
        given:
        Candidate candidate = null

        when:
        def result = candidateService.create(candidate)

        then:
        result.error != null
    }
}

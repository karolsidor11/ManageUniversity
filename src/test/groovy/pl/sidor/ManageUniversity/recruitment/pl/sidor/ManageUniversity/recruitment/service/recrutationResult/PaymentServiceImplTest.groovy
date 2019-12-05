package pl.sidor.ManageUniversity.recruitment.service.recrutationResult

import configuration.data.TestCandidateData
import configuration.data.TestPaymentForStudy
import pl.sidor.ManageUniversity.dto.CandidateDto
import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.recruitment.model.Candidate
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo
import pl.sidor.ManageUniversity.recruitment.service.payments.PaymentServiceImpl
import spock.lang.Ignore
import spock.lang.Specification

import javax.persistence.EntityManager

class PaymentServiceImplTest extends Specification {

    private PaymentRepo paymentRepo
    private PaymentServiceImpl paymentService
    private EntityManager entityManager

    void setup() {

        entityManager = Mock(EntityManager.class)
        paymentRepo = Mock(PaymentRepo.class)
        paymentService = new PaymentServiceImpl(entityManager, paymentRepo)

    }

    @Ignore
    def "should pay for study"() {
        given:
        PaymentForStudy payment = TestPaymentForStudy.preparePaymentForStudy()
        Candidate candidate = TestCandidateData.prepareCandidate()

        when:
        paymentRepo.save(payment) >> payment

        def result = paymentService.pay(payment)

        then:
        result != null
    }

    def "should throw exception when payment is null"() {
        given:
        PaymentForStudy payment = null

        when:
        paymentRepo.save(paymentRepo) >> payment
        paymentService.pay(payment)

        then:
        Exception exception = thrown(UniversityException.class)
        exception.message.equals("Przekazywany obiekt jest pusty.:PaymentForStudy.")
    }

    def "should  check payments"() {

        given:
        CandidateDto candidateDto = TestCandidateData.prepareCandidateDto()
        PaymentForStudy payment = TestPaymentForStudy.preparePaymentForStudy()

        when:
        paymentRepo.findByNameAndLastName(candidateDto.getName(), candidateDto.getLastName()) >> Optional.of(payment)
        def result = paymentService.checkPayments(candidateDto.getName(), candidateDto.getLastName())

        then:
        result != null
        result.getName().equals("Karol")
        result.getLastName().equals("Sidor")
        result.getEmail().equals("karolsidor11@wp.pl")
    }

    def "should throw exception when payments is empty"() {

        given:
        CandidateDto candidateDto = TestCandidateData.prepareCandidateDto()

        when:
        paymentRepo.findByNameAndLastName(candidateDto.getName(), candidateDto.getLastName()) >> Optional.empty()
        paymentService.checkPayments(candidateDto.getName(), candidateDto.getLastName())

        then:
        thrown(UniversityException.class)
    }
}
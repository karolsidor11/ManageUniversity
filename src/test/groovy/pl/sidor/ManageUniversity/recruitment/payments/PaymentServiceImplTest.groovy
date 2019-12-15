package pl.sidor.ManageUniversity.recruitment.payments

import pl.sidor.ManageUniversity.dto.CandidateDto
import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.recruitment.model.Candidate
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo
import pl.sidor.ManageUniversity.recruitment.service.payments.PaymentServiceImpl
import pl.sidor.ManageUniversity.utils.TestCandidateData
import pl.sidor.ManageUniversity.utils.TestPaymentForStudy
import spock.lang.Ignore
import spock.lang.Specification

import javax.persistence.EntityManager

class PaymentServiceImplTest extends Specification {

    private PaymentRepo paymentRepo=Mock(PaymentRepo.class)
    private EntityManager entityManager=Stub()
    private PaymentServiceImpl paymentService=[entityManager,paymentRepo]

    @Ignore
    def "should pay for study"() {
        given:
        PaymentForStudy payment = TestPaymentForStudy.preparePaymentForStudy()
        Candidate candidate = TestCandidateData.prepareCandidate()

        when:
        def result = paymentService.pay(payment)

        then:
        result != null
    }

    def "should throw exception when payment is null"() {
        given:
        PaymentForStudy payment = null

        when:
        paymentService.pay(payment)

        then:
        Exception exception = thrown(UniversityException.class)
        exception.message == "Przekazywany obiekt jest pusty.:PaymentForStudy."
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
        result.getName() == "Karol"
        result.getLastName() == "Sidor"
        result.getEmail() == "karolsidor11@wp.pl"
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

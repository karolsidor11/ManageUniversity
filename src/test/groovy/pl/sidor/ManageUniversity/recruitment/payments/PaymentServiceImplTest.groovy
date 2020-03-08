package pl.sidor.ManageUniversity.recruitment.payments

import pl.sidor.ManageUniversity.dto.CandidateDto
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo
import pl.sidor.ManageUniversity.recruitment.service.payments.PaymentServiceImpl
import pl.sidor.ManageUniversity.utils.TestCandidateData
import pl.sidor.ManageUniversity.utils.TestPaymentForStudy
import spock.lang.Specification

import javax.persistence.EntityManager

class PaymentServiceImplTest extends Specification {

    private PaymentRepo paymentRepo = Mock(PaymentRepo.class)
    private EntityManager entityManager = Stub()
    private PaymentServiceImpl paymentService = [entityManager, paymentRepo]

    def "should pay for study"() {
        given:
        PaymentForStudy payment = TestPaymentForStudy.preparePaymentForStudy()

        when:
        paymentRepo.save(_ as PaymentForStudy) >> payment
        def result = paymentService.pay(payment)

        then:
        result.error == null
    }

    def "should throw exception when payment is null"() {
        given:
        PaymentForStudy payment = null

        when:
        def result = paymentService.pay(payment)

        then:
        result.error != null
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
        result.getPaymentForStudy().getName() == "Karol"
        result.getPaymentForStudy().getLastName() == "Sidor"
        result.getPaymentForStudy().getEmail() == "karolsidor11@wp.pl"
    }

    def "should throw exception when payments is empty"() {
        given:
        CandidateDto candidateDto = TestCandidateData.prepareCandidateDto()

        when:
        paymentRepo.findByNameAndLastName(candidateDto.getName(), candidateDto.getLastName()) >> Optional.empty()
        def result = paymentService.checkPayments(candidateDto.getName(), candidateDto.getLastName())

        then:
        result.error != null
    }
}

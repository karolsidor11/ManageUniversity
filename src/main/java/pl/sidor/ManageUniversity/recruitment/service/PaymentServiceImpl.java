package pl.sidor.ManageUniversity.recruitment.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo paymentRepo;

    @Override
    public PaymentForStudy pay(PaymentForStudy paymentForStudy) throws UniversityException {
        if (Objects.isNull(paymentForStudy)) { throw ExceptionFactory.objectIsEmpty("PaymentForStudy."); }
        return paymentRepo.save(paymentForStudy);
    }

    @Override
    public PaymentForStudy checkPayments(String name, String lastName) throws Throwable {

        Optional<PaymentForStudy> byNameAndLastName = paymentRepo.findByNameAndLastName(name, lastName);

        return byNameAndLastName.orElseThrow(ExceptionFactory.objectIsEmpty("Brak płatności"));
    }
}

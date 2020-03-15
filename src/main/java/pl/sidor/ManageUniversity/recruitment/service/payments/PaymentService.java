package pl.sidor.ManageUniversity.recruitment.service.payments;

import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse pay(final PaymentForStudy paymentForStudy);

    PaymentResponse checkPayments(final String name, final String lastName);
}

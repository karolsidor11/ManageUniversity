package pl.sidor.ManageUniversity.recruitment.service.payments;

import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;

public interface PaymentService {

    PaymentForStudy pay(final PaymentForStudy paymentForStudy) throws Throwable;

    PaymentForStudy checkPayments(final String name, final String lastName) throws Throwable;
}

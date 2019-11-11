package pl.sidor.ManageUniversity.recruitment.service;

import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;

public interface PaymentService {

    PaymentForStudy pay(PaymentForStudy paymentForStudy) throws UniversityException;

    PaymentForStudy checkPayments(String name, String lastName) throws Throwable;
}

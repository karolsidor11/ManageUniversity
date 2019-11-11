package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.dto.CandidateDto;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.service.PaymentService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "payments")
public class PaymentsController {

    private PaymentService paymentService;

    @PostMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentForStudy> makePayment(@RequestBody PaymentForStudy payment) throws UniversityException {
        return new ResponseEntity<>(paymentService.pay(payment), HttpStatus.OK);
    }

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentForStudy> checkPayments(@RequestBody CandidateDto candidateDto) throws Throwable {

        assert  candidateDto!=null;
        PaymentForStudy paymentForStudy = paymentService.checkPayments(candidateDto.getName(), candidateDto.getLastName());

        return new ResponseEntity<>(paymentForStudy, HttpStatus.OK);
    }
}

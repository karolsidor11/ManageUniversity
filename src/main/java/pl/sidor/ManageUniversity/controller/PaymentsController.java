package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.dto.CandidateDto;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.response.PaymentResponse;
import pl.sidor.ManageUniversity.recruitment.service.payments.PaymentService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "payments")
public class PaymentsController {

    private final PaymentService paymentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentForStudy payment) {
        return new ResponseEntity<>(paymentService.pay(payment), HttpStatus.OK);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> checkPayments(@RequestBody CandidateDto candidateDto) {
        PaymentResponse paymentResponse = paymentService.checkPayments(candidateDto.getName(), candidateDto.getLastName());
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}

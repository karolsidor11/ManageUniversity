package pl.sidor.ManageUniversity.recruitment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;

import java.util.Optional;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class PaymentResponse {

    private final Header header;
    private final PaymentForStudy paymentForStudy;
    private final Error error;

    public static PaymentResponse prepareResponse(Optional<PaymentForStudy> payment, Error error) {
        return payment.isPresent() ? buildSuccessResponse(payment.get()) : buildErrorResponse(error);
    }

    private static PaymentResponse buildSuccessResponse(PaymentForStudy payment) {
        return PaymentResponse.builder().header(Header.getInstance()).paymentForStudy(payment).build();
    }

    private static PaymentResponse buildErrorResponse(Error error) {
        return PaymentResponse.builder().header(Header.getInstance()).error(error).build();
    }
}

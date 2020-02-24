package pl.sidor.ManageUniversity.utils;

import pl.sidor.ManageUniversity.recruitment.enums.StudyDirection;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;

public class TestPaymentForStudy {

    public  static  PaymentForStudy preparePaymentForStudy(){
        return PaymentForStudy.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .price(3500L)
                .studyDirection(StudyDirection.INFORMATYKA)
                .build();
    }
}

package pl.sidor.ManageUniversity.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo;
import pl.sidor.ManageUniversity.utils.TestPaymentForStudy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentsControllerIT {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Ignore
    public  void  should_make_a_payments_for_study() throws Exception {

        // given
        PaymentForStudy paymentForStudy = TestPaymentForStudy.preparePaymentForStudy();

        //expected
        mockMvc.perform(post("/payments")
                .content(objectMapper.writeValueAsString(paymentForStudy))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Karol")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Sidor")))
                .andExpect(jsonPath("$.email", Matchers.is("karolsidor11@wp.pl")))
                .andReturn();
    }

    @Test
    public  void  should_throw_exception_make_a_payments_for_study() throws Exception {

        // given
        PaymentForStudy paymentForStudy = null;

        //expected
        mockMvc.perform(post("/payments")
                .content(objectMapper.writeValueAsString(paymentForStudy))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_checkpayments() throws Exception {

        // given
        PaymentForStudy paymentForStudy = TestPaymentForStudy.preparePaymentForStudy();
        paymentRepo.save(paymentForStudy);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/payments")
                .content(objectMapper.writeValueAsString(paymentForStudy))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Karol")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Sidor")))
                .andExpect(jsonPath("$.email", Matchers.is("karolsidor11@wp.pl")))
                .andReturn();
    }

    @Test
    public void should_throw_checkpayments() throws Exception {

        // given
        PaymentForStudy paymentForStudy = null;

        //expected
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/payments")
                .content(objectMapper.writeValueAsString(paymentForStudy))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}

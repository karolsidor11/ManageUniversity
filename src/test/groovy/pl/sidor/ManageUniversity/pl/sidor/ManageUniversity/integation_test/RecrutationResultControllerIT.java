package pl.sidor.ManageUniversity.integation_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.data.TestCandidateData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.sidor.ManageUniversity.dto.CandidateDto;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecrutationResultControllerIT {

    @MockBean
    private RecrutationResultRepo recrutationResultRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public  void  should_check_recrutation_result() throws Exception {

        //given
        CandidateDto candidateDto = TestCandidateData.prepareCandidateDto();
        RecrutationResult recrutationResult = RecrutationResult.builder()
                .name(candidateDto.getName())
                .lastName(candidateDto.getLastName())
                .isAccept(AcceptedInCollage.ACCEPTED)
                .build();

        when(recrutationResultRepo.findByNameAndLastName(candidateDto.getName(), candidateDto.getLastName()))
                .thenReturn(recrutationResult);

        //expected

        mockMvc.perform(get("/recrutationResult")
                .content(objectMapper.writeValueAsString(candidateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public  void  should_throw_exception() throws Exception {

        // given
        CandidateDto candidateDto =TestCandidateData.prepareCandidateDto();
        when(recrutationResultRepo.findByNameAndLastName("Jan", "Nowak")).thenReturn(null);

        //expected
        mockMvc.perform(get("/recruitmentResult")
                .content(objectMapper.writeValueAsString(candidateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}

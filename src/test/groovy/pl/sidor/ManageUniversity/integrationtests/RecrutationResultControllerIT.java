package pl.sidor.ManageUniversity.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.sidor.ManageUniversity.utils.TestCandidateData;

import java.util.Optional;

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
                .thenReturn(Optional.of(recrutationResult));

        //expected
        mockMvc.perform(get("/recrutationResults")
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

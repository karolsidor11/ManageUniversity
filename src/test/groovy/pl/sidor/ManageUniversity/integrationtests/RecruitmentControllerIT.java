package pl.sidor.ManageUniversity.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo;
import pl.sidor.ManageUniversity.utils.TestCandidateData;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecruitmentControllerIT {

    @MockBean
    private CandidateRepo candidateRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_find_candidate_by_id() throws Exception {

        // given
        Candidate candidate = TestCandidateData.prepareCandidate();
        when(candidateRepo.findById(candidate.getId())).thenReturn(Optional.of(candidate));

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments/{id}",candidate.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Karol")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Sidor")))
                .andExpect(jsonPath("$.email", Matchers.is("karolsidor11@wp.pl")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_find_candidate_by_id() throws Exception {

        // given
        when(candidateRepo.findById(9999L)).thenReturn(Optional.empty());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments/{id}",99999L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public  void should_delete_candidate() throws Exception {

        // given
        Long  id =1L;
        Candidate candidate = TestCandidateData.prepareCandidate();
        when(candidateRepo.findById(id)).thenReturn(Optional.of(candidate));

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/recruitments/{id}",candidate.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public  void should_throw_exception_delete_candidate() throws Exception {

        // given
        Long  id =9999L;
        when(candidateRepo.findById(id)).thenReturn(Optional.empty());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/recruitments/{id}",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void  should_create_candidate() throws Exception {

        // given
        Candidate candidate = TestCandidateData.prepareCandidate();
        when(candidateRepo.save(candidate)).thenReturn(candidate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/recruitments")
                .content(objectMapper.writeValueAsString(candidate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Karol")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Sidor")))
                .andExpect(jsonPath("$.email", Matchers.is("karolsidor11@wp.pl")))
                .andReturn();
    }

    @Test
    public void  should_throw_exception_create_candidate() throws Exception {

        // given
        Candidate candidate = null;

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/recruitments")
                .content(objectMapper.writeValueAsString(candidate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}

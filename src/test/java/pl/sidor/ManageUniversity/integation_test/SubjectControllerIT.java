package pl.sidor.ManageUniversity.integation_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerIT {

    @MockBean
    private SubjectRepo subjectRepo;

    @MockBean
    private SubjectValidator subjectValidator;

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_find_subject_by_id() throws Exception {

        // given :
        Subject subject = Subject.builder()
                .id(1L)
                .name("Polski")
                .roomNumber(11)
                .build();

        when(subjectRepo.findById(1L)).thenReturn(Optional.of(subject));

        // when:
        MvcResult mvcResult = mockMvc.perform(get("/subject/findSubject/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is(1)))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Subject subject1 = objectMapper.readValue(contentAsString, Subject.class);

        //then:

        verify(subjectRepo, times(1)).findById(1L);
        assertEquals(subject, subject1);

    }

//    TODO do zrobienia NULL POINTER EXCEPTION
    @Test
    @Ignore
    public void should_save_subject() throws Exception {

//        given:
        Subject subject = Subject.builder()
                .id(1L)
                .name("Matematyka")
                .roomNumber(22)
                .build();

        when(subjectValidator.test(subject)).thenReturn(false);
        when(subjectRepo.save(subject)).thenReturn(subject);

        mockMvc.perform(post("/subject/create")
        .content(objectMapper.writeValueAsString(subject))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        verify(subjectRepo, times(1)).save(subject);

    }
}

package pl.sidor.ManageUniversity.integation_test;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static configuration.data.TestLecturerData.prepareLecturer;
import static configuration.data.TestSubjectData.prepareSubject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerIT {

    @MockBean
    private SubjectRepo subjectRepo;

    @MockBean
    private SubjectValidator subjectValidator;

    @MockBean
    private LecturerRepo lecturerRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void should_find_subject_by_id() throws Exception {

        // when
        Subject subject =prepareSubject(prepareLecturer());
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(subject));

        // expected
         mockMvc.perform(get("/subject/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is(1)))
                .andReturn();
    }

    @Test
    public  void  should_throw_exception_when_find_by_id() throws Exception {

        // when
        Long id=999L;

        when(subjectRepo.findById(id)).thenReturn(Optional.empty());

        // expected
        mockMvc.perform(get("/schedule/{id}", id))
            .andExpect(status().isNotFound())
            .andReturn();

    }

    @Test
    public void should_delete_subject() throws Exception {

        // when
        Long id= 1L;

        when(subjectRepo.findById(id)).thenReturn(Optional.of(Subject.builder().id(1L).build()));
        doNothing().when(subjectRepo).deleteById(id);

        // expected
        mockMvc.perform(delete("/subject/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        verify(subjectRepo,times(1)).deleteById(id);
    }

    @Test
    public void  should_throw_exception_when_delete() throws Exception {

        // when
        Long id=1L;

        when(subjectRepo.findById(id)).thenReturn(Optional.empty());
        doNothing().when(subjectRepo).deleteById(id);

        // expected

        mockMvc.perform(delete("/subject/{id}", id))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_fin_by_lecturer() throws Exception {

        //  when
        Lecturer lecturer= prepareLecturer();

        when(lecturerRepo.findById(lecturer.getId())).thenReturn(Optional.of(lecturer));
        when(subjectRepo.findByLecturer(lecturer)).thenReturn(Optional.ofNullable(prepareSubject(lecturer)));

        // expeted
        mockMvc.perform(get("/subject/lecturer/{id}", lecturer.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public  void should_throw_exception_when_find_by_lecturer() throws Exception {

        //  when
        Lecturer lecturer= prepareLecturer();

        when(lecturerRepo.findById(lecturer.getId())).thenReturn(Optional.empty());
        when(subjectRepo.findByLecturer(lecturer)).thenReturn(Optional.of(prepareSubject(lecturer)));


        //  expeted
        mockMvc.perform(get("/schedule/lecturer/{id}", lecturer.getId()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public  void  should_create_subject() throws Exception {

        // when
        Subject subject = prepareSubject();
        when(subjectValidator.test(subject)).thenReturn(true);
        when(subjectRepo.save(subject)).thenReturn(subject);

        // expected

        mockMvc.perform(post("/subject/save")
                .content(objectMapper.writeValueAsString(subject))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public  void  test_should_throw_excepton_when_save_subject() throws Exception {

        // when
        Subject subject = null;
        when(subjectRepo.save(subject)).thenReturn(subject);

        // expected

        mockMvc.perform(post("/subject/save")
                .content(objectMapper.writeValueAsString(subject))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}

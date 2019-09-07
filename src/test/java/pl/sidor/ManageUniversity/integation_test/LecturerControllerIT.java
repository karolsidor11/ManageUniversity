package pl.sidor.ManageUniversity.integation_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
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
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;

import java.awt.*;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LecturerControllerIT {

    @MockBean
    private LecturerRepo lecturerRepo;

    @MockBean
    private CheckLecturer checkLecturer;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_find_lecturer_by_id() throws Exception {

        //given:
        Lecturer lecturer = Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Kowalski")
                .email("kowalski@wp.pl")
                .grade("Doktor")
                .build();

        when(lecturerRepo.findById(1L)).thenReturn(Optional.of(lecturer));

        //when:
        MvcResult mvcResult = mockMvc.perform(get("/findLecturer/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name",Matchers.is("Jan")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Kowalski")))
                .andExpect(jsonPath("$.email",Matchers.is("kowalski@wp.pl")))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Lecturer lecturer1 = objectMapper.readValue(contentAsString, Lecturer.class);

        //then:
        verify(lecturerRepo, times(1)).findById(1L);
        assertEquals(lecturer, lecturer1);

    }

    @Test
    public void should_save_lecturer() throws Exception {

    // given:
        Lecturer lecturer= Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();

        when(checkLecturer.test(lecturer)).thenReturn(true);
        when(lecturerRepo.save(lecturer)).thenReturn(lecturer);

    //when:
      MvcResult result=  mockMvc.perform(post("/saveLecturer")
                .content(objectMapper.writeValueAsString(lecturer))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Jan")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Nowak")))
                .andExpect(jsonPath("$.email", Matchers.is("nowak@wp.pl")))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Lecturer lecturer1 = objectMapper.readValue(contentAsString, Lecturer.class);

        //then:
        verify(lecturerRepo,times(1)).save(lecturer);

        assertNotNull(lecturer1);
        assertEquals(lecturer, lecturer1);

    }

    @Test
    public void should_delete_lecturer() throws Exception {

    // given:

        Long id= 1L;

        when(lecturerRepo.findById(id)).thenReturn(Optional.of(Lecturer.builder().id(id).build()));
        doNothing().when(lecturerRepo).deleteById(id);

    //when:
        mockMvc.perform(delete("/deleteLecturer/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

    //then:
        verify( lecturerRepo, times(1)).deleteById(id);
    }

    @Test
     public  void should_update_lecturer() throws Exception {

//        given:
        Lecturer lecturer= Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Kowalski")
                .email("kowalski@wp.pl")
                .build();

        Lecturer updateLecturer= Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();

        when(lecturerRepo.findById(1L)).thenReturn(Optional.of(lecturer));
        when(lecturerRepo.save(updateLecturer)).thenReturn(updateLecturer);

        //when:
        mockMvc.perform(post("/updateLecturer")
                .content(objectMapper.writeValueAsString(updateLecturer))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        // then:
        verify(lecturerRepo, times(1)).save(any(Lecturer.class));

     }
}

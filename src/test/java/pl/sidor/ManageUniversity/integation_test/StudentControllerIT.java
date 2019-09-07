package pl.sidor.ManageUniversity.integation_test;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepo studentRepo;

    @MockBean
    private CheckUniqeStudentPredicate checkUniqeStudentPredicate;

    private  ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper= new ObjectMapper();
    }

    @Test
    public void should_find_student_by_id() throws Exception {

    // given :
        Student student= Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .build();

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

    // expect:
         mockMvc.perform(get("/findStudent/1", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andReturn();
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//        Student student1 = objectMapper.readValue(contentAsString, Student.class);

    //then:

//        verify(studentRepo,times(1)).findById(1L);
//       assertEquals(student,student1);

    }

    @Test
    public void should_save_student() throws Exception {

    // given:
        Student student = Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build();
        when(checkUniqeStudentPredicate.test(student)).thenReturn(true);
        when(studentRepo.save(student)).thenReturn(student);

    //when:
        MvcResult mvcResult = mockMvc.perform(post("/saveStudent")
                .content(objectMapper.writeValueAsString(student))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Karol")))
                .andExpect(jsonPath("$.lastName", is("Sidor")))
                .andExpect(jsonPath("$.email", is("karolsidor11@wp.pl")))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Student student1 = objectMapper.readValue(contentAsString, Student.class);

        //then:

        verify(studentRepo, times(1)).save(student);
        assertNotNull(student1);
        assertEquals(student, student1);

    }

    @Test
    public void should_delete_student() throws Exception {

    // given:

        when(studentRepo.findById(1L)).thenReturn(Optional.of(Student.builder().id(1l).build()));
        doNothing().when(studentRepo).deleteById(1L);

    //  when:

        mockMvc.perform(delete("/deleteStudent/{id}",1))
                .andExpect(status().isOk());


    // then:
        verify(studentRepo, times(1)).deleteById(anyLong());

    }

    @Test
    public void should_update_student() throws Exception {

    //given:
        Student student = Student.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();

        Student updateStudent = Student.builder()
                .id(1L)
                .name("Marek")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();

        when(checkUniqeStudentPredicate.test(student)).thenReturn(true);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepo.save(updateStudent)).thenReturn(updateStudent);

    //when:

     MvcResult mvcResult= mockMvc.perform(post("/updateStudent")
                 .content(objectMapper.writeValueAsString(updateStudent))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    // then:
        verify(studentRepo, times(1)).save(updateStudent);
        assertEquals(200, mvcResult.getResponse().getStatus());

    }
}

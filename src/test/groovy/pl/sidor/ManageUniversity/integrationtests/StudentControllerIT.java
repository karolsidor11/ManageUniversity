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
import org.springframework.test.web.servlet.MvcResult;
import pl.sidor.ManageUniversity.exception.MessageException;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sidor.ManageUniversity.exception.MessageException.W_BAZIE_BRAK_STUDENTA;
import static pl.sidor.ManageUniversity.utils.TestScheduleData.prepareSchedule;
import static pl.sidor.ManageUniversity.utils.TestStudentData.*;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_find_student_by_id() throws Exception {

        // given
        Student student = prepareStudent();

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        // expect
        mockMvc.perform(get("/students/1", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id", is(1)))
                .andReturn();
    }

    @Test
    public  void test_should_throw_exception_when_incorrect_id() throws Exception {

        // given
        Long studentID=9830984L;
        when(studentRepo.findById(studentID)).thenReturn(Optional.empty());

        // expect
        mockMvc.perform(get("/students/{id}", studentID))
                .andDo(print())
                .andExpect(jsonPath("$.error.description",
                        is("W bazie nie istnieje STUDENT o podanym id.:9830984")))
                .andReturn();
    }

    @Test
    public void should_save_student() throws Exception {

        // given
        Student student = prepareStudent();
        when(checkUniqeStudentPredicate.test(student)).thenReturn(false);
        when(studentRepo.save(student)).thenReturn(student);

        // when
         mockMvc.perform(post("/students")
                .content(objectMapper.writeValueAsString(student))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(jsonPath("$.student.id", is(1)))
                .andExpect(jsonPath("$.student.name", is("Karol")))
                .andExpect(jsonPath("$.student.lastName", is("Sidor")))
                .andExpect(jsonPath("$.student.email", is("karolsidor11@wp.pl")))
                .andReturn();
    }

    @Test
    public void test_should_throw_exception_when_save_empty_student() throws Exception {

        // when
        when(checkUniqeStudentPredicate.test(null)).thenReturn(true);

        // expect
        mockMvc.perform(post("/students")
                .content(objectMapper.writeValueAsString(null))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_delete_student() throws Exception {

        // given
        when(studentRepo.findById(1L)).thenReturn(Optional.of(Student.builder().id(1L).build()));
        doNothing().when(studentRepo).deleteById(1L);

        //  when
        mockMvc.perform(delete("/students/{id}", 1))
                .andExpect(status().isOk());

        // then
        verify(studentRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public  void  test_should_throw_exception_delete_student() throws Exception {

        // given
        Long studentId=9999L;

        // expect
        mockMvc.perform(delete("/students/{id}", studentId))
                .andExpect(jsonPath("$.error.description",
                        is("W bazie nie istnieje STUDENT o podanym id.:9999")))
                .andReturn();
    }

    @Test
    public void should_update_student() throws Exception {

        // given
        Student student = prepareStudent();
        Student updateStudent = prepareUpdateStudent();

        when(checkUniqeStudentPredicate.test(student)).thenReturn(true);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepo.save(updateStudent)).thenReturn(updateStudent);

        // when
       mockMvc.perform(post("/students")
                        .content(objectMapper.writeValueAsString(updateStudent))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
        // then
        verify(studentRepo, times(1)).save(updateStudent);

    }

    @Test
    public void test_should_throw_exception_update_student() throws Exception {

        // given
        Student student=prepareStudent();
        Student newStudent= prepareUpdateStudent();

        when(studentRepo.findById(student.getId())).thenReturn(Optional.empty());

        //  expect
        mockMvc.perform(post("/students")
                .content(objectMapper.writeValueAsString(newStudent))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}

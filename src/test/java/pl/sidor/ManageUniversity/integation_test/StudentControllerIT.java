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
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    private ScheduleRepo scheduleRepo;

    @MockBean
    private CheckUniqeStudentPredicate checkUniqeStudentPredicate;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_find_student_by_id() throws Exception {

        // given
        Student student = Student.builder().id(1L).name("Karol").lastName("Sidor").build();

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        // expect
        mockMvc.perform(get("/findStudent/1", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andReturn();
    }

    @Test
    public  void test_should_throw_exception_when_incorrect_id() throws Exception {

        // given
        Long studentID=9830984L;
        when(studentRepo.findById(studentID)).thenReturn(Optional.empty());

        // expect
        mockMvc.perform(get("/findStudent/{id}", studentID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_save_student() throws Exception {

        // given
        Student student = getStudent();
        when(checkUniqeStudentPredicate.test(student)).thenReturn(true);
        when(studentRepo.save(student)).thenReturn(student);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/saveStudent")
                .content(objectMapper.writeValueAsString(student))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Karol")))
                .andExpect(jsonPath("$.lastName", is("Sidor")))
                .andExpect(jsonPath("$.email", is("karolsidor11@wp.pl")))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Student student1 = objectMapper.readValue(contentAsString, Student.class);

        // then
        verify(studentRepo, times(1)).save(student);
        assertNotNull(student1);
        assertEquals(student, student1);

    }

    @Test
    public void test_should_throw_exception_when_save_empty_student() throws Exception {

        // given
        Student student = null;

        when(checkUniqeStudentPredicate.test(student)).thenReturn(true);

        // expect
        mockMvc.perform(post("/saveStudent")
                .content(objectMapper.writeValueAsString(student))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_delete_student() throws Exception {

        // given
        when(studentRepo.findById(1L)).thenReturn(Optional.of(Student.builder().id(1l).build()));
        doNothing().when(studentRepo).deleteById(1L);

        //  when
        mockMvc.perform(delete("/deleteStudent/{id}", 1)).andExpect(status().isOk());

        // then
        verify(studentRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public  void  test_should_throw_exception_delete_student() throws Exception {

        // given
        Long studentId=9999L;

        // expect
        mockMvc.perform(delete("/deleteStudent/{id}", studentId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_update_student() throws Exception {

        // given
        Student student = Student.builder().id(1L).name("Jan").lastName("Nowak").email("nowak@wp.pl").build();

        Student updateStudent = Student.builder().id(1L).name("Marek").lastName("Nowak").email("nowak@wp.pl").build();

        when(checkUniqeStudentPredicate.test(student)).thenReturn(true);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepo.save(updateStudent)).thenReturn(updateStudent);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/updateStudent")
                        .content(objectMapper.writeValueAsString(updateStudent))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
        // then
        verify(studentRepo, times(1)).save(updateStudent);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_should_throw_exception_update_student() throws Exception {

        // given
        Student student = Student.builder()
                .id(1L)
                .name("Jan")
                .build();

        Student newStudent= Student.builder()
                .id(1L)
                .name("Mariusz")
                .lastName("Nowak")
                .build();

        when(studentRepo.findById(student.getId())).thenReturn(Optional.empty());

        //  expect
        mockMvc.perform(post("updateStudent")
                .content(objectMapper.writeValueAsString(newStudent))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldFindScheduleForStudent() throws Exception {

        //given
        FindScheduleRequest request = getFindScheduleRequest();

        Student student = getStudent();

        when(studentRepo.findByNameAndLastName(request.getName(), request.getLastName())).thenReturn(Optional.of(student));
        when(scheduleRepo.findByStudentGroupAndWeekNumber(2.2, 12)).thenReturn(getSchedule());

        // expected
        mockMvc.perform(get("/findSchedule/student").content(objectMapper.writeValueAsString(request))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].dayOfWeek", is("Poniedzialek")));
    }

    @Test
    public void shouldNotFindScheduleForStudent() throws Exception {

        // given
        FindScheduleRequest request= getFindScheduleRequest();
        Student student = getStudent();

        when(studentRepo.findByNameAndLastName(request.getName(), request.getLastName())).thenReturn(Optional.of(student));
        when(scheduleRepo.findByStudentGroupAndWeekNumber(2.2, 12)).thenReturn(Collections.emptyList());

        // expected
        MvcResult result = mockMvc.perform(get("/findSchedule/student")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void testShouldThrowExceptionWhenStudentNameIsNull() throws Exception {

        // given
        FindScheduleRequest request = FindScheduleRequest.builder()
                .name(null)
                .lastName(null)
                .weekNumber(12)
                .build();

        // expected
        MvcResult result = mockMvc.perform(get("/findSchedule/student")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void  testShouldThrowExceptionWhenWeekNumberIsEmpty() throws Exception {

        // given
        Student student= getStudent();
        FindScheduleRequest request= getFindScheduleRequest();
        request.setWeekNumber(null);

        List<Schedule> schedules= Collections.singletonList(Schedule.builder().id(1L).studentGroup(2.2).build());

        when(studentRepo.findByNameAndLastName(student.getName(), student.getLastName())).thenReturn(Optional.of(student));
        when(scheduleRepo.findByStudentGroupAndWeekNumber(2.2, 12)).thenReturn(schedules);

        // expected
        MvcResult result = mockMvc.perform(get("/findSchedule/student")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertEquals(result.getResponse().getStatus(), 404);
    }

    private Student getStudent() {
        return Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .studentGroup(2.2)
                .build();
    }
    private static FindScheduleRequest getFindScheduleRequest() {
        return FindScheduleRequest.builder()
                .name("Karol")
                .lastName("Sidor")
                .weekNumber(12)
                .build();
    }

    private  static List<Schedule> getSchedule(){
        return Collections.singletonList(Schedule.builder().
                id(1L)
                .dayOfWeek(Days.Poniedzialek).studentGroup(2.2)
                .weekNumber(12).build()
        );
    }
}

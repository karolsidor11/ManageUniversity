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
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LecturerControllerIT {

    @MockBean
    private LecturerRepo lecturerRepo;

    @MockBean
    private SubjectRepo subjectRepo;

    @MockBean
    private ScheduleRepo scheduleRepo;

    @MockBean
    private CheckLecturer checkLecturer;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

//    todo  sprawdz przy braku id
    @Test
    public void should_find_lecturer_by_id() throws Exception {

        // given:
        Lecturer lecturer = Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Kowalski")
                .email("kowalski@wp.pl")
                .grade("Doktor")
                .build();

        when(lecturerRepo.findById(1L)).thenReturn(Optional.of(lecturer));

        // when:
        MvcResult mvcResult = mockMvc.perform(get("/findLecturer/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name",Matchers.is("Jan")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Kowalski")))
                .andExpect(jsonPath("$.email",Matchers.is("kowalski@wp.pl")))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Lecturer lecturer1 = objectMapper.readValue(contentAsString, Lecturer.class);

        // then:
        verify(lecturerRepo, times(1)).findById(1L);
        assertEquals(lecturer, lecturer1);

    }

//    todo Test na puste pola -> sprawdzenei czy podano imie, nazwisko , email
    @Test
    public void should_save_lecturer() throws Exception {

    // given
        Lecturer lecturer= Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();

        when(checkLecturer.test(lecturer)).thenReturn(true);
        when(lecturerRepo.save(lecturer)).thenReturn(lecturer);

    // when
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

        // then
        verify(lecturerRepo,times(1)).save(lecturer);

        assertNotNull(lecturer1);
        assertEquals(lecturer, lecturer1);

    }

//    todo  sprawdz przy braku id
    @Test
    public void should_delete_lecturer() throws Exception {

    // given:
        Long id= 1L;

        when(lecturerRepo.findById(id)).thenReturn(Optional.of(Lecturer.builder().id(id).build()));
        doNothing().when(lecturerRepo).deleteById(id);

    // when:
        mockMvc.perform(delete("/deleteLecturer/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

    // then:
        verify( lecturerRepo, times(1)).deleteById(id);
    }

//    todo  update  brak uzytkownika w bazie, sprawdz czy przy update pozostałę pola się zachowaja
    @Test
     public  void should_update_lecturer() throws Exception {

        // given:
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

        // when:
        mockMvc.perform(post("/updateLecturer")
                .content(objectMapper.writeValueAsString(updateLecturer))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        // then:
        verify(lecturerRepo, times(1)).save(any(Lecturer.class));

     }

//     todo przy barku id
     @Test
     public void testShouldFindLecturerDTO() throws Exception {

        // given:
         Lecturer lecturer= Lecturer.builder()
                 .id(1L)
                 .name("Jan")
                 .lastName("Kowalski")
                 .email("jan@wp.pl")
                 .grade("Doktor")
                 .build();
          when(lecturerRepo.findById(lecturer.getId())).thenReturn(Optional.of(lecturer));

        // expected:
         mockMvc.perform(get("/lecturerDTO/1", 1L))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name", Matchers.is("Jan")))
                 .andExpect(jsonPath("$.lastName", Matchers.is("Kowalski")))
                 .andExpect(jsonPath("$.email", Matchers.is("jan@wp.pl")))
                 .andExpect(jsonPath("$.grade", Matchers.is("Doktor")))
                 .andReturn();

     }

     @Test
     public void testShouldFindScheduleForLecturer() throws Exception {

        // given:
         Lecturer lecturer = getLecturer();

         FindScheduleRequest request= getRequest(lecturer);

         Subject subject= getSubject(lecturer);


         Schedule schedule = getSchdule(subject);


         when(lecturerRepo.findByNameAndLastName(lecturer.getName(), lecturer.getLastName())).thenReturn(Optional.of(lecturer));
         when(subjectRepo.findByLecturer(lecturer)).thenReturn(Optional.of(subject));
         when(scheduleRepo.findBySubjects(subject)).thenReturn(Arrays.asList(schedule));

        // expected:
         mockMvc.perform(get("/findSchedule/lecturer")
                 .content(objectMapper.writeValueAsString(request))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(status().isOk())
                 .andReturn();
     }

//     todo nieprawidlowy lektor
     @Test
     public void testShouldThrowExceptionWhenSearchScheduleForLecturer() throws Exception {

        // given:
         Lecturer lecturer= getLecturer();
         FindScheduleRequest request = getRequest(lecturer);

         when(lecturerRepo.findByNameAndLastName(lecturer.getName(), lecturer.getLastName())).thenReturn(Optional.of(lecturer));
         when(subjectRepo.findByLecturer(lecturer)).thenReturn(Optional.of(getSubject(lecturer)));

        // expected:
         mockMvc.perform(get("/findSchedule/lecturer")
                 .content(objectMapper.writeValueAsString(request))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(status().isNotFound())
                 .andReturn();
     }

    private Schedule getSchdule(Subject subject) {
        return Schedule.builder()
                .id(1L)
                .studentGroup(2.2)
                .dayOfWeek(Days.Poniedzialek)
                .subjects(Collections.singletonList(subject))
                .weekNumber(12)
                .build();
    }

    private Subject getSubject(Lecturer lecturer) {
        return Subject.builder()
                .id(1L)
                .name("Java")
                .roomNumber(22)
                .lecturer(Collections.singletonList(lecturer))
                .build();
    }

    private FindScheduleRequest getRequest(Lecturer lecturer) {
        return FindScheduleRequest.builder()
                .name(lecturer.getName())
                .lastName(lecturer.getLastName())
                .weekNumber(12)
                .build();
    }

    private Lecturer getLecturer() {
        return Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Kowalski")
                .grade("Doktor")
                .email("jan@wp.pl")
                .build();
    }
}

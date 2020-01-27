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
import org.springframework.test.web.servlet.MvcResult;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sidor.ManageUniversity.utils.TestLecturerData.prepareLecturer;
import static pl.sidor.ManageUniversity.utils.TestLecturerData.updatelecturer;
import static pl.sidor.ManageUniversity.utils.TestScheduleData.prepareScheduleRequest;
import static pl.sidor.ManageUniversity.utils.TestSubjectData.prepareSubject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LecturerControllerIT {

    @MockBean
    private LecturerRepo lecturerRepo;

    @MockBean
    private SubjectRepo subjectRepo;

    @MockBean
    private CheckLecturer checkLecturer;

    @Autowired
    private MockMvc mockMvc;
    
    private ObjectMapper objectMapper= new ObjectMapper();

    @Test
    public void should_find_lecturer_by_id() throws Exception {

        // given
        Lecturer lecturer = prepareLecturer();
        when(lecturerRepo.findById(1L)).thenReturn(Optional.of(lecturer));

        // expected
        mockMvc.perform(get("/lecturers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lecturer.id", Matchers.is(1)))
                .andExpect(jsonPath("$.lecturer.name", Matchers.is("Jan")))
                .andExpect(jsonPath("$.lecturer.lastName", Matchers.is("Kowalski")))
                .andExpect(jsonPath("$.lecturer.email", Matchers.is("kowalski@wp.pl")))
                .andReturn();
    }

    @Test
    public void test_should_throw_exception_when_id_is_incorrect() throws Exception {

        // when
        Long id = 9999L;

        when(lecturerRepo.findById(id)).thenReturn(Optional.empty());

        // expected
        mockMvc.perform(get("lecturers/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_save_lecturer() throws Exception {

        // given
        Lecturer lecturer =prepareLecturer();

        when(lecturerRepo.findByEmail(lecturer.getEmail())).thenReturn(null);
        when(checkLecturer.test(lecturer)).thenReturn(true);
        when(lecturerRepo.save(lecturer)).thenReturn(lecturer);

        // when
         mockMvc.perform(post("/lecturers")
                .content(objectMapper.writeValueAsString(lecturer))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.lecturer.id", Matchers.is(1)))
                .andExpect(jsonPath("$.lecturer.name", Matchers.is("Jan")))
                .andExpect(jsonPath("$.lecturer.lastName", Matchers.is("Kowalski")))
                .andExpect(jsonPath("$.lecturer.email", Matchers.is("kowalski@wp.pl")))
                 .andReturn();
    }

    @Test
    public void test_should_throw_exception_when_lecturer_null() throws Exception {

        // given
        Lecturer lecturer = prepareLecturer();

        when(checkLecturer.test(lecturer)).thenReturn(true);

        // then
        mockMvc.perform(post("lecturers").content(objectMapper.writeValueAsString(lecturer))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_delete_lecturer() throws Exception {

        // given
        Long id = 1L;

        when(lecturerRepo.findById(id)).thenReturn(Optional.of(prepareLecturer()));
        doNothing().when(lecturerRepo).deleteById(id);

        // when
        mockMvc.perform(delete("/lecturers/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        // then
        verify(lecturerRepo, times(1)).deleteById(id);
    }

    @Test
    public void test_should_throw_exception_when_id_incorrect() throws Exception {

        // when
        Long id = 123212L;

        when(lecturerRepo.findById(id)).thenReturn(Optional.empty());
        doNothing().when(lecturerRepo).deleteById(id);

        // expected
        mockMvc.perform(delete("lecturers/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_update_lecturer() throws Exception {

        // given
        Lecturer lecturer = prepareLecturer();
        Lecturer updateLecturer = updatelecturer();

        when(lecturerRepo.findById(1L)).thenReturn(Optional.of(lecturer));
        when(lecturerRepo.save(updateLecturer)).thenReturn(updateLecturer);

        // when
        mockMvc.perform(post("/lecturers").content(objectMapper.writeValueAsString(updateLecturer))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        // then
        verify(lecturerRepo, times(1)).save(any(Lecturer.class));
    }

    @Test
    public void test_should_throw_exception_when_update_lecturer() throws Exception {

        //  given
        Lecturer lecturer = prepareLecturer();
        Lecturer updateLecturer = updatelecturer();

        when(lecturerRepo.findById(lecturer.getId())).thenReturn(Optional.empty());

        //  expect
        mockMvc.perform(post("/lecturers").content(objectMapper.writeValueAsString(updateLecturer))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testShouldFindLecturerDTO() throws Exception {

        // given
        LecturerDTO lecturerDTO = LecturerMapper.mapTo(prepareLecturer());

        when(lecturerRepo.findById(prepareLecturer().getId())).thenReturn(Optional.of(prepareLecturer()));

        // expected
        mockMvc.perform(get("/lecturers/lecturerDTO/{id}", prepareLecturer().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lecturer.name", Matchers.is("Jan")))
                .andExpect(jsonPath("$.lecturer.lastName", Matchers.is("Kowalski")))
                .andExpect(jsonPath("$.lecturer.email", Matchers.is("kowalski@wp.pl")))
                .andExpect(jsonPath("$.lecturer.grade", Matchers.is("Doktor")))
                .andReturn();
    }

    @Test
    public void test_should_throw_exception_when_lecturerDto_incorrect_id() throws Exception {

        // given
        Long id = 2324343L;

        when(lecturerRepo.findById(id)).thenReturn(Optional.empty());

        //  when
        MvcResult result = mockMvc.perform(get("lecturers/lecturerDTO/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        // then
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void testShouldThrowExceptionWhenSearchScheduleForLecturer() throws Exception {

        // given
        Lecturer lecturer = prepareLecturer();
        FindScheduleRequest request = prepareScheduleRequest(lecturer);

        when(lecturerRepo.findByNameAndLastName(lecturer.getName(), lecturer.getLastName())).thenReturn(Optional.of(lecturer));
        when(subjectRepo.findByLecturer(lecturer)).thenReturn(Optional.of(prepareSubject(lecturer)));

        // expected
        mockMvc.perform(get("lecturers/findSchedule").content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void test_should_throw_exception_if_lecturer_incorrect() throws Exception {

        // given
        FindScheduleRequest request = prepareScheduleRequest(prepareLecturer());

        when(lecturerRepo.findByNameAndLastName("Jan", "Nowakowski")).thenReturn(Optional.empty());
        when(subjectRepo.findByLecturer(null)).thenReturn(Optional.empty());

        //  expected
        mockMvc.perform(get("lecturers/findSchedule").content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
}

package pl.sidor.ManageUniversity.integation_test;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerIT {

    @MockBean
    private ScheduleRepo scheduleRepo;

    @MockBean
    private ScheduleValidator scheduleValidator;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_find_schedule_by_id() throws Exception {


        //given:
        Schedule schedule = Schedule.builder().id(1L).dayOfWeek(Days.Poniedzialek).subjects(Collections.emptyList()).build();


        when(scheduleRepo.findById(1L)).thenReturn(Optional.of(schedule));

        // when:
        mockMvc.perform(get("/schedule/1")).andExpect(status().isOk()).andExpect(jsonPath("$.dayOfWeek", Matchers.is("Poniedzialek"))).andReturn();

        //then:
        verify(scheduleRepo, times(1)).findById(1L);
    }


    @Test
    public void should_find_schedule_by_day() throws Exception {

        //given:
        Schedule schedule = Schedule.builder().id(1L).dayOfWeek(Days.Czwartek).subjects(Collections.emptyList()).build();


        when(scheduleRepo.findByDayOfWeek(Days.Czwartek)).thenReturn(Optional.of(schedule));

        //when:

        MvcResult result = mockMvc.perform(get("/schedule/getByDay/Czwartek")).andExpect(status().isOk()).andExpect(jsonPath("$.dayOfWeek", Matchers.is("Czwartek"))).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Schedule schedule1 = objectMapper.readValue(contentAsString, Schedule.class);

        //then:
        verify(scheduleRepo, times(1)).findByDayOfWeek(any(Days.class));

        assertNotNull(schedule1);
        assertEquals(schedule, schedule1);

    }

    @Test
    public void should_create_schedule() throws Exception {

        //given:
        Schedule schedule = Schedule.builder().id(1L).dayOfWeek(Days.Czwartek).subjects(Collections.emptyList()).build();

        when(scheduleValidator.test(Days.Czwartek)).thenReturn(true);
        when(scheduleRepo.save(schedule)).thenReturn(schedule);

        // when:

        MvcResult result = mockMvc.perform(post("/schedule/create").content(objectMapper.writeValueAsString(schedule)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andExpect(jsonPath("$.id", Matchers.is(1))).andExpect(jsonPath("$.dayOfWeek", Matchers.is("Czwartek"))).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Schedule schedule1 = objectMapper.readValue(contentAsString, Schedule.class);

        //then:

        verify(scheduleRepo, times(1)).save(any(Schedule.class));

        assertNotNull(schedule1);
        assertEquals(schedule, schedule1);
    }


    @Test
    public void delete_schedule_by_id() throws Exception {

        //when:
        Long id = 1l;

        when(scheduleRepo.findById(id)).thenReturn(Optional.of(Schedule.builder().id(1L).build()));
        doNothing().when(scheduleRepo).deleteById(id);


        //when:
        MvcResult result = mockMvc.perform(delete("/schedule/delete/1")).andExpect(status().isOk()).andReturn();


        String contentAsString = result.getResponse().getContentAsString();
        Boolean aBoolean = objectMapper.readValue(contentAsString, Boolean.class);
        //then:
        verify(scheduleRepo, times(1)).deleteById(id);

        assertEquals(aBoolean, true);
    }

    @Test
    public void delete_by_day() throws Exception {

        //given:

        Days day = Days.Czwartek;

        when(scheduleRepo.findByDayOfWeek(day)).thenReturn(Optional.ofNullable(Schedule.builder().id(1L).dayOfWeek(day).build()));
        doNothing().when(scheduleRepo).deleteByDayOfWeek(day);

        //when:

        mockMvc.perform(delete("/schedule/deleteBy/Czwartek")).andExpect(status().isOk()).andReturn();

        //then:

        verify(scheduleRepo, times(1)).deleteByDayOfWeek(day);

    }

    @Test
    public void should_update_schedule() throws Exception {

        //given:

        Schedule schedule = Schedule.builder().id(1L).dayOfWeek(Days.Poniedzialek).subjects(Collections.emptyList()).build();


        Schedule updateSchedule = Schedule.builder()

                .id(1L).dayOfWeek(Days.Poniedzialek).subjects(Arrays.asList(Subject.builder().id(1L).name("Polski").roomNumber(11).build())).build();

        when(scheduleRepo.findByDayOfWeek(Days.Poniedzialek)).thenReturn(Optional.of(schedule));
        when(scheduleRepo.save(updateSchedule)).thenReturn(updateSchedule);


        //when:

        MvcResult result = mockMvc.perform(post("/schedule/update").content(objectMapper.writeValueAsString(updateSchedule)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andExpect(jsonPath("$.id", Matchers.is(1))).andExpect(jsonPath("$.dayOfWeek", Matchers.is("Poniedzialek"))).andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Schedule schedule1 = objectMapper.readValue(contentAsString, Schedule.class);

        //then:

        verify(scheduleRepo, times(1)).save(updateSchedule);
        assertNotNull(schedule1);
        assertEquals(updateSchedule, schedule1);

    }

    @Test
    @Ignore //todo Do weryfikacji NullPointerException
    public  void  test_should_modify_schedule() throws Exception {

        //  given

        ScheduleUpdate scheduleUpdate = ScheduleUpdate.builder()
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(Subject.builder().id(1L).name("Polski").build())
                .build();

        when(scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.getDayOfWeek(),
                scheduleUpdate.getWeekNumber())).thenReturn(Optional.of(prepareSchedule()));

        when(scheduleRepo.save(prepareSchedule())).thenReturn(prepareSchedule());

        //  expect

        mockMvc.perform(post("/schedule/modifySchedule")
                .content(objectMapper.writeValueAsString(scheduleUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public  void  test_should_throw_exception_modify_schedule() throws Exception {

        //  given

        ScheduleUpdate scheduleUpdate = ScheduleUpdate.builder()
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(Subject.builder().id(1L).name("Polski").build())
                .build();

        when(scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.getDayOfWeek(),
                scheduleUpdate.getWeekNumber())).thenReturn(Optional.empty());

        //  expect

        mockMvc.perform(post("/schedule/modifySchedule")
                .content(objectMapper.writeValueAsString(scheduleUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private Schedule prepareSchedule(){
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.builder().id(1L).build());

        return Schedule.builder()
                .id(1L)
                .weekNumber(12)
                .dayOfWeek(Days.Poniedzialek)
                .subjects(subjectList)
                .studentGroup(2.2)
                .build();
    }
}

package pl.sidor.ManageUniversity.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sidor.ManageUniversity.utils.TestScheduleData.*;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_find_schedule_by_id() throws Exception {
        //given:
        Schedule schedule = prepareSchedule();
        when(scheduleRepo.findById(1L)).thenReturn(Optional.of(schedule));

        // when:
        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedule.dayOfWeek", Matchers.is("Poniedzialek")))
                .andReturn();

        //then:
        verify(scheduleRepo, times(1)).findById(1L);
    }

    @Test
    public void should_find_schedule_by_day() throws Exception {
        //given:
        Schedule schedule = prepareSchedule();
        when(scheduleRepo.findByDayOfWeek(Days.Poniedzialek)).thenReturn(Optional.of(schedule));

        //when:
         mockMvc.perform(get("/schedules/day/Poniedzialek"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedule.dayOfWeek", Matchers.is("Poniedzialek")))
                .andReturn();
    }

    @Test
    public void should_create_schedule() throws Exception {
        //given:
        Schedule schedule = prepareSchedule();

        when(scheduleValidator.test(Days.Poniedzialek)).thenReturn(false);
        when(scheduleRepo.save(schedule)).thenReturn(schedule);

        // when:
        mockMvc.perform(post("/schedules").content(objectMapper.writeValueAsString(schedule))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.schedule.id", Matchers.is(1)))
                .andExpect(jsonPath("$.schedule.dayOfWeek", Matchers.is("Poniedzialek")))
                .andReturn();
    }

    @Test
    public void delete_schedule_by_id() throws Exception {
        //when:
        Long id = 1L;
        when(scheduleRepo.findById(id)).thenReturn(Optional.of(prepareSchedule()));
        doNothing().when(scheduleRepo).deleteById(id);

        //when:
        mockMvc.perform(delete("/schedules/1"))
                .andExpect(status().isOk())
                .andReturn();

        //then:
        verify(scheduleRepo, times(1)).deleteById(id);
    }

    @Test
    public void delete_by_day() throws Exception {
        //given:
        Days day = Days.Poniedzialek;

        when(scheduleRepo.findByDayOfWeek(day)).thenReturn(Optional.ofNullable(prepareSchedule()));
        doNothing().when(scheduleRepo).deleteByDayOfWeek(day);

        //when:
        mockMvc.perform(delete("/schedules/delete/Poniedzialek"))
                .andExpect(status().isOk())
                .andReturn();

        //then:
        verify(scheduleRepo, times(1)).deleteByDayOfWeek(day);

    }

    @Test
    public void should_update_schedule() throws Exception {
        //given:
        Schedule schedule =prepareSchedule();
        Schedule updateSchedule = prepareScheduleUpdate();

        when(scheduleRepo.findByDayOfWeek(Days.Poniedzialek)).thenReturn(Optional.of(schedule));
        when(scheduleRepo.save(updateSchedule)).thenReturn(updateSchedule);

        //when:
        mockMvc.perform(put("/schedules").content(objectMapper.writeValueAsString(updateSchedule))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.schedule.id", Matchers.is(1)))
                .andExpect(jsonPath("$.schedule.dayOfWeek", Matchers.is("Poniedzialek")))
                .andReturn();
    }

    @Ignore
    @Test
    public void test_should_modify_schedule() throws Exception {
        //  given
        ScheduleUpdate scheduleUpdate = prepareScheduleUpdates();

        when(scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.getDayOfWeek(), scheduleUpdate.getWeekNumber()))
                .thenReturn(Optional.of(prepareSchedule()));
        when(scheduleRepo.save(prepareSchedule())).thenReturn(prepareSchedule());

        //  expect
        mockMvc.perform(patch("/schedules").content(objectMapper.writeValueAsString(scheduleUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    public void test_should_throw_exception_modify_schedule() throws Exception {
        //  when
        ScheduleUpdate scheduleUpdate = prepareScheduleUpdates();
        when(scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.getDayOfWeek(), scheduleUpdate.getWeekNumber())).thenReturn(Optional.empty());

        //  expect
        mockMvc.perform(patch("/schedules").content(objectMapper.writeValueAsString(scheduleUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andReturn();
    }
}

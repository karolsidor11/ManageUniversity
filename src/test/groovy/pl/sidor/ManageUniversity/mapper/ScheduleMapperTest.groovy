package pl.sidor.ManageUniversity.mapper

import pl.sidor.ManageUniversity.dto.ScheduleDTO
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import spock.lang.Specification

class ScheduleMapperTest extends Specification {

    def "should convert Schedule"() {
        given:
        Schedule schedule = getSchedule()

        when:
        ScheduleDTO scheduleDTO = ScheduleMapper.mapTo(schedule)

        then:
        scheduleDTO != null
        scheduleDTO.studentGroup == 2.2
        scheduleDTO.getDayOfWeek() == Days.Czwartek
    }

    private static Schedule getSchedule() {
        return Schedule.builder()
                .id(1L)
                .dayOfWeek(Days.Czwartek)
                .studentGroup(2.2)
                .build()
    }
}

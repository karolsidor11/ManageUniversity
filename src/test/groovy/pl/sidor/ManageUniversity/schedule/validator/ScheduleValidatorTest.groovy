package pl.sidor.ManageUniversity.schedule.validator

import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import spock.lang.Specification
import spock.lang.Unroll

class ScheduleValidatorTest extends Specification {

    private ScheduleRepo scheduleRepo = Mock(ScheduleRepo.class)
    private ScheduleValidator scheduleValidator = [scheduleRepo]

    @Unroll
    def "should return #expectResult when #dane"() {
        given:
        Days days = Days.Poniedzialek
        scheduleRepo.findByDayOfWeek(days) >> Optional.of(getSchedule(days))

        expect:
        expectResult == scheduleValidator.test(dane)

        where:
        dane              || expectResult
        Days.Wtorek       || true
        Days.Poniedzialek || false
        Days.Sroda        || true
        Days.Czwartek     || true
        Days.Piatek       || true
    }

    private static Schedule getSchedule(Days days) {
        return Schedule.builder()
                .id(1)
                .dayOfWeek(days)
                .build()
    }
}

package ManageUniversity.schedule.validator

import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator
import spock.lang.Specification
import spock.lang.Unroll

class ScheduleValidatorTest extends Specification {

    private ScheduleRepo scheduleRepo
    private ScheduleValidator scheduleValidator

    void setup() {
        scheduleRepo = Mock(ScheduleRepo.class)
        scheduleValidator = new ScheduleValidator(scheduleRepo)
    }

    @Unroll
    def "should return excpectResult"() {

        given:
        Days days = Days.Poniedzialek

        scheduleRepo.findByDayOfWeek(days) >> Optional.of(Schedule.builder().id(1).dayOfWeek(days).build())

        expect:
        result == scheduleValidator.test(dane)

        where:
        dane              || result
        Days.Wtorek       || true
        Days.Poniedzialek || false
        Days.Sroda        || true
        Days.Czwartek     || true
        Days.Piatek       || true
    }

}

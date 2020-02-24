package pl.sidor.ManageUniversity.schedule.validator

import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.utils.ScheduleUtils
import spock.lang.Specification
import spock.lang.Unroll

class ScheduleValidatorTest extends Specification {

    private ScheduleRepo scheduleRepo = Mock(ScheduleRepo.class)
    private ScheduleValidator scheduleValidator = [scheduleRepo]

    @Unroll
    def "should return #expectResult when #dane"() {
        given:
        Days days = Days.Poniedzialek
        scheduleRepo.findByDayOfWeek(Days.Poniedzialek) >> ScheduleUtils.getSchedule(days)
        scheduleRepo.findByDayOfWeek(_ as Days) >> Optional.empty()

        expect:
        isScheduleInDatabase == scheduleValidator.test(dane)

        where:
        dane              || isScheduleInDatabase
        Days.Wtorek       || false
        Days.Poniedzialek || true
        Days.Sroda        || false
        Days.Czwartek     || false
        Days.Piatek       || false
    }


}

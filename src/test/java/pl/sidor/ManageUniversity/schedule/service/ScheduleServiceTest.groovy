package ManageUniversity.schedule.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.service.ScheduleService
import pl.sidor.ManageUniversity.schedule.service.ScheduleServiceImpl
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator
import spock.lang.Specification

class ScheduleServiceTest extends Specification {

    private ScheduleRepo scheduleRepo
    private ScheduleService scheduleService
    private ScheduleValidator scheduleValidator

    void setup() {
        scheduleRepo = Mock(ScheduleRepo.class)
        scheduleValidator=Mock(ScheduleValidator.class)
        scheduleService = new ScheduleServiceImpl(scheduleRepo,scheduleValidator)
    }

    def " should  delete Schedule  by ID"() {

        given:
        Long id = 12
        Schedule schedule = Schedule.builder()
                .id(12)
                .dayOfWeek(Days.Poniedzialek)
                .build()

        scheduleRepo.findById(id) >> Optional.of(schedule)

        when:
        def result = scheduleService.deleteByID(id)

        then:
        result == true
    }

    def " should throw Exception when  try delete Schedule by ID"() {

        given:
        Long id = 9898
        scheduleRepo.findById(id) >> Optional.empty()

        when:
        scheduleService.deleteByID(id)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_BRAK_PLANU:" + id
    }

    def "should  delete schedule by day"() {

        given:
        Days days = Days.Poniedzialek
        scheduleRepo.findByDayOfWeek(days) >> Optional.of(Schedule.builder()
                .id(1)
                .dayOfWeek(days)
                .build())
        scheduleRepo.deleteByDayOfWeek(days)

        when:
        scheduleService.deleteByDay(days)

        then:
        1 * scheduleRepo.deleteByDayOfWeek(_)
    }

    def "should throw exception when delete schedule"() {

        given:
        Days days = Days.Czwartek

        scheduleRepo.findByDayOfWeek(days) >> Optional.empty()
        scheduleRepo.deleteByDayOfWeek(days)

        when:
        scheduleService.deleteByDay(days)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_BRAK_PLANU_O_PODANYM_DNIU:" + days.toString()
    }


    def "should find schedule by ID"() {
        given:

        Schedule schedule = Schedule.builder()
                .id(1L)
                .dayOfWeek(Days.Poniedzialek)
                .build()

        scheduleRepo.findById(1L) >> Optional.of(schedule)

        when:
        def actualSchedule = scheduleService.getScheduleById(1L)

        then:
        actualSchedule != null
        actualSchedule == schedule
    }


    def "should throw when Schedule id is incorrect"() {

        given:
        Long id = 999
        scheduleRepo.findById(id) >> Optional.empty()

        when:

        scheduleService.getScheduleById(id)

        then:
        thrown(UniversityException.class)

    }
}

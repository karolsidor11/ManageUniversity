package pl.sidor.ManageUniversity.schedule.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.request.ScheduleUpdate
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.model.Subject
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator
import spock.lang.Specification

import java.time.LocalTime

class ScheduleServiceTest extends Specification {

    private ScheduleRepo scheduleRepo = Mock(ScheduleRepo.class)
    private ScheduleValidator scheduleValidator = Mock(ScheduleValidator.class)
    private ScheduleServiceImpl scheduleService = [scheduleRepo, scheduleValidator]

    def "should  delete Schedule by ID"() {
        given:
        Schedule schedule = prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.of(schedule)

        when:
        def result = scheduleService.deleteByID(schedule.id)

        then:
        result
    }

    def "should throw Exception when try delete Schedule by ID"() {
        given:
        Schedule schedule = prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.empty()

        when:
        scheduleService.deleteByID(schedule.id)

        then:
        UniversityException exception = thrown()
        exception.message == "W_BAZIE_BRAK_PLANU:" + schedule.id
    }

    def "should  delete schedule by day"() {
        given:
        Days days = Days.Poniedzialek
        scheduleRepo.findByDayOfWeek(days) >> Optional.of(prepareSchedule())
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
        Schedule schedule = prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.of(schedule)

        when:
        def actualSchedule = scheduleService.getScheduleById(schedule.id)

        then:
        actualSchedule != null
        actualSchedule == schedule
    }


    def "should throw when Schedule id is incorrect"() {
        given:
        Schedule schedule = prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.empty()

        when:
        scheduleService.getScheduleById(schedule.id)

        then:
        thrown(UniversityException.class)
    }

    def "test should modify Schedule"() {
        given:
        ScheduleUpdate scheduleUpdate = prepareModifyRequest()

        scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.dayOfWeek, scheduleUpdate.weekNumber) >> Optional.of(prepareSchedule())
        scheduleRepo.save(Schedule.builder().build()) >> Optional.of(prepareSchedule())

        when:
        Schedule schedule = scheduleService.modifySchedule(scheduleUpdate)

        then:
        schedule != null
    }

    def "test  should  throw exception during modify Schedule"() {
        given:
        ScheduleUpdate scheduleUpdate = prepareModifyRequest()
        scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.dayOfWeek, scheduleUpdate.weekNumber) >> Optional.empty()

        when:
        scheduleService.modifySchedule(scheduleUpdate)

        then:
        thrown(UniversityException.class)
    }

    private static ScheduleUpdate prepareModifyRequest() {
        ScheduleUpdate.builder()
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(prepareSubject())
                .build()
    }

    private static Subject prepareSubject() {
        return Subject.builder()
                .id(1L)
                .name("Polski")
                .roomNumber(21)
                .startTime(LocalTime.now())
                .endTime(LocalTime.of(12, 20))
                .build()
    }

    private static Schedule prepareSchedule() {
        List<Subject> subjectList = new ArrayList<>()
        subjectList.add(prepareSubject())

        return Schedule.builder()
                .id(1l)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .subjects(subjectList)
                .build()
    }
}

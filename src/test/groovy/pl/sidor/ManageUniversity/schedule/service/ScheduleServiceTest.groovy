package pl.sidor.ManageUniversity.schedule.service

import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo
import pl.sidor.ManageUniversity.request.ScheduleUpdate
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo
import pl.sidor.ManageUniversity.schedule.utils.ScheduleUtils
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator
import pl.sidor.ManageUniversity.student.service.StudentService
import spock.lang.Specification

class ScheduleServiceTest extends Specification {

    private ScheduleRepo scheduleRepo = Mock(ScheduleRepo.class)
    private ScheduleValidator scheduleValidator = Mock(ScheduleValidator.class)
    private LecturerRepo lecturerRepo = Mock()
    private SubjectRepo subjectRepo = Mock()
    private StudentService studentService = Mock()

    private ScheduleServiceImpl scheduleService = [scheduleRepo, lecturerRepo,  subjectRepo, studentService, scheduleValidator]

    def "should  delete Schedule by ID"() {
        given:
        Schedule schedule = ScheduleUtils.prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.of(schedule)

        when:
        def result = scheduleService.deleteByID(schedule.id)

        then:
        result.error == null
    }

    def "should throw Exception when try delete Schedule by ID"() {
        given:
        Schedule schedule = ScheduleUtils.prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.empty()

        when:
        def result = scheduleService.deleteByID(schedule.id)

        then:
        result.error != null
    }

    def "should  delete schedule by day"() {
        given:
        Days days = Days.Poniedzialek
        scheduleRepo.findByDayOfWeek(days) >> Optional.of(ScheduleUtils.prepareSchedule())
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
        def result = scheduleService.deleteByDay(days)

        then:
        result.error != null
    }

    def "should find schedule by ID"() {
        given:
        Schedule schedule = ScheduleUtils.prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.of(schedule)

        when:
        def actualSchedule = scheduleService.getScheduleById(schedule.id)

        then:
        actualSchedule != null
        actualSchedule.schedule == schedule
    }


    def "should throw when Schedule id is incorrect"() {
        given:
        Schedule schedule = ScheduleUtils.prepareSchedule()
        scheduleRepo.findById(schedule.id) >> Optional.empty()

        when:
        def result = scheduleService.getScheduleById(schedule.id)

        then:
        result.error != null
    }

    def "test should modify Schedule"() {
        given:
        ScheduleUpdate scheduleUpdate = ScheduleUtils.prepareModifyRequest()

        scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.dayOfWeek, scheduleUpdate.weekNumber) >> Optional.of(ScheduleUtils.prepareSchedule())
        scheduleRepo.save(Schedule.builder().build()) >> Optional.of(ScheduleUtils.prepareSchedule())

        when:
        Schedule schedule = scheduleService.modifySchedule(scheduleUpdate).schedule

        then:
        schedule != null
    }

    def "test  should  throw exception during modify Schedule"() {
        given:
        ScheduleUpdate scheduleUpdate = ScheduleUtils.prepareModifyRequest()
        scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.dayOfWeek, scheduleUpdate.weekNumber) >> Optional.empty()

        when:
        def result = scheduleService.modifySchedule(scheduleUpdate)

        then:
        result.error != null
    }
}

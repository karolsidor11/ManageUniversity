package pl.sidor.ManageUniversity.student.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.request.FindScheduleRequest
import pl.sidor.ManageUniversity.schedule.enums.Days
import pl.sidor.ManageUniversity.schedule.model.Schedule
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo
import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate
import spock.lang.Specification

class StudentServiceImplTest extends Specification {

    private StudentRepo studentRepo = Mock(StudentRepo.class)
    private ScheduleRepo scheduleRepo = Mock(ScheduleRepo.class)
    private CheckUniqeStudentPredicate studentValidator = Mock(CheckUniqeStudentPredicate.class)

    private StudentServiceImpl service = [studentRepo, studentValidator, scheduleRepo]

    def "should find student by ID "() {
        given:
        Student student = getStudent()
        studentRepo.findById(1) >> Optional.of(student)

        when:
        Student actualStudent = service.findById(1)

        then:
        actualStudent != null
        actualStudent.id == 1
        actualStudent.name == "Karol"
        actualStudent.lastName == "Sidor"
    }

    def "should throw Exception when id is incorrect"() {
        given:
        Long id = -1
        studentRepo.findById(id) >> Optional.empty()

        when:
        service.findById(id)

        then:
        thrown(UniversityException.class)
    }

    def "should create student"() {
        given:
        Student student = getStudent()
        studentValidator.test(student) >> false
        studentRepo.save(student) >> student

        when:
        Student actualStudent = service.create(student)

        then:
        actualStudent == student
        actualStudent.name == "Karol"
        actualStudent.lastName == "Sidor"
    }

    def "should  not create student"() {
        given:
        Student student = null
        studentValidator.test(student) >> true
        studentRepo.save(student) >> student

        when:
        service.create(student)

        then:
        UniversityException exception = thrown()
        exception.message == "Przekazywany obiekt jest pusty.:!!!"
    }

    def "should throw StudentException"() {
        given:
        Student student = getStudent()
        studentValidator.test(student) >> true
        studentRepo.save(student) >> student

        when:
        service.create(student)

        then:
        thrown(UniversityException.class)
    }

    def "should delete student"() {
        given:
        Student student = getStudent()
        studentRepo.findById(student.id) >> Optional.of(getStudent())
        studentRepo.deleteById(student.id)

        when:
        service.delete(student.id)

        then:
        1 * studentRepo.deleteById(student.id)
    }

    def "should throw Exception when student id is incorrect"() {
        given:
        Student student = getStudent()
        studentRepo.findById(student.id) >> Optional.empty()

        when:
        service.delete(student.id)

        then:
        thrown(UniversityException.class)
    }

    def "should update student"() {
        given:
        Student student = getStudent()
        Student studentSecondVersion = getStudent2()
        studentRepo.findById(student.id) >> Optional.of(student)
        studentRepo.save(studentSecondVersion) >> studentSecondVersion

        when:
        service.update(studentSecondVersion)

        then:
        1 * studentRepo.save(studentSecondVersion)
    }

    def "should throw Exception during update student"() {
        given:
        Student student = getStudent()
        studentRepo.findById(student.id) >> Optional.empty()

        when:
        Student actualStudent = service.findById(student.id)
        service.update(actualStudent)

        then:
        thrown(UniversityException.class)
    }

    def "test should find schedule for student"() {
        given:
        Student student = getStudent()
        Schedule schedule = getSchedule()
        FindScheduleRequest request = getRequest()

        studentRepo.findByNameAndLastName(request.getName(), request.getLastName()) >> Optional.of(student)
        scheduleRepo.findByStudentGroupAndWeekNumber(student.getStudentGroup(), 12) >> Arrays.asList(schedule)

        when:
        def result = service.findScheduleForStudent(request)

        then:
        result != null
        result.weekNumber.get(0) == 12
        result.studentGroup.get(0) == 2.3
    }

    def " test should find schedule for Student"() {
        given:
        Student student = getStudent()
        Schedule schedule = getSchedule()
        studentRepo.findByNameAndLastName("Karol", "Sidor") >> Optional.of(student)
        scheduleRepo.findByStudentGroupAndWeekNumber(student.getStudentGroup(), 12) >> Arrays.asList(schedule)

        when:
        List<Schedule> actualSchedule = service.findScheduleForStudent(getRequest())

        then:
        actualSchedule != null
        actualSchedule.get(0).weekNumber == 12
        actualSchedule.size() == 1
    }

    def "test should throw Exception"() {
        given:
        Student student = getStudent()
        studentRepo.findByNameAndLastName(_ as String, _ as String) >> Optional.of(student)
        scheduleRepo.findByStudentGroupAndWeekNumber(student.studentGroup, _ as Integer) >> Collections.emptyList()

        when:
        service.findScheduleForStudent(getRequest())

        then:
        UniversityException exception = thrown()
        exception.message == "Wystąpił nieoczekiwany błąd systemu. Nie znaleziono rozkładu dla podanych parametrów :  Karol Sidor 12"
    }

    private static Student getStudent() {
        Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .studentGroup(new Double(2.3 as double))
                .build()
    }

    private static Student getStudent2() {
        return Student.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .build()
    }

    private static FindScheduleRequest getRequest() {
        FindScheduleRequest.builder()
                .name("Karol")
                .lastName("Sidor")
                .weekNumber(12).build()
    }

    private static Schedule getSchedule() {
        return Schedule.builder()
                .id(1)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .studentGroup(2.3 as Double)
                .build()
    }
}

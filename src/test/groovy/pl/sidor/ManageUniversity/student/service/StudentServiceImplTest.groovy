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

    private StudentRepo studentRepo
    private StudentServiceImpl service
    private ScheduleRepo scheduleRepo
    private CheckUniqeStudentPredicate studentValidator

    void setup() {
        studentRepo = Mock(StudentRepo.class)
        scheduleRepo = Mock(ScheduleRepo.class)
        studentValidator = Mock(CheckUniqeStudentPredicate.class)
        service = new StudentServiceImpl(studentRepo, studentValidator, scheduleRepo)
    }

    def "should find student by ID "() {

        given:
        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build()
        studentRepo.findById(1) >> Optional.of(student)

        when:
        Student actualStudent = service.findById(1)

        then:
        actualStudent != null
        actualStudent.id == 1
    }

    def "should  throw Exception when id is incorrect"() {
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
        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build()

        studentValidator.test(student) >> false
        studentRepo.save(student) >> student

        when:
        Student actualStudent = service.create(student)

        then:
        actualStudent == student
    }

    def "should  not create student"() {
        given:
        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build()

        studentValidator.test(student) >> true
        studentRepo.save(student) >> student

        when:
        service.create(null)

        then:
        UniversityException exception = thrown()
        exception.message == "Przekazywany obiekt jest pusty.:!!!"
    }


    def "should throw StudentException"() {

        given:
        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build()

        studentValidator.test(student) >> true
        studentRepo.save(student) >> student

        when:
        service.create(student)

        then:
        thrown(UniversityException.class)
    }

    def "should delete student"() {

        given:
        long id = 1
        studentRepo.findById(id) >> Optional.of(Student.builder().id(1).build())
        studentRepo.deleteById(id)

        when:
        service.delete(id)

        then:
        1 * studentRepo.deleteById(id)
    }

    def "should throw Exception when student id is incorrect"() {

        given:
        Long studentId = 999
        studentRepo.findById(studentId) >> Optional.empty()

        when:
        service.delete(studentId)

        then:
        thrown(UniversityException.class)
    }

    def "should update student"() {
        given:
        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build()

        studentRepo.findById(1) >> Optional.of(student)

        Student studentSecondVersion = Student.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .build()

        studentRepo.save(studentSecondVersion) >> studentSecondVersion
        when:
        service.update(studentSecondVersion)

        then:
        1 * studentRepo.save(studentSecondVersion)
    }

    def "should throw Exception during update student"() {

        given:
        Long id = 9999
        studentRepo.findById(id) >> Optional.empty()

        when:
        Student actualStudent = service.findById(id)
        actualStudent.setName("Jan")
        actualStudent.setLastName("Nowak")
        actualStudent.setEmail("nowak@wp.pl")

        service.update(actualStudent)

        then:
        thrown(UniversityException.class)
    }

    def "test should find schedule for student"() {

        given:

        Student student = Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .studentGroup(new Double(2.3 as double))
                .build()

        Schedule schedule = Schedule.builder()
                .id(1L)
                .dayOfWeek(Days.Czwartek)
                .studentGroup(new Double(2.3 as double))
                .weekNumber(12)
                .build()

       FindScheduleRequest request= getRequest()

        studentRepo.findByNameAndLastName(request.getName(), request.getLastName()) >> Optional.of(student)
        scheduleRepo.findByStudentGroupAndWeekNumber(student.getStudentGroup(), 12) >> Arrays.asList(schedule)

        when:

        def result = service.findScheduleForStudent(request)

        then:
        result != null
        result.weekNumber.get(0) == 12
        result.studentGroup.get(0) == 2.3


    }

    private static FindScheduleRequest getRequest() {
        FindScheduleRequest.builder()
                .name("Karol")
                .lastName("Sidor")
                .weekNumber(12).build()
    }

    def " test should find schedule for Student"() {
        given:
        Student student = getStudent()

        Schedule schedule = Schedule.builder()
                .id(1)
                .dayOfWeek(Days.Poniedzialek)
                .weekNumber(12)
                .build()
        studentRepo.findByNameAndLastName("Karol", "Sidor") >> Optional.of(student)
        scheduleRepo.findByStudentGroupAndWeekNumber(student.getStudentGroup(), 12) >> Arrays.asList(schedule)

        when:

        List<Schedule> actualSchedule = service.findScheduleForStudent(getRequest())

        then:
        actualSchedule != null
        actualSchedule.get(0).weekNumber == 12
        actualSchedule.size() == 1
    }

    private static Student getStudent() {
        Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .build()
    }

    def "test should throw Exception"() {

        given:
        Student student = getStudent()

        studentRepo.findByNameAndLastName("Karol","Sidor")>>Optional.of(student)
        scheduleRepo.findByStudentGroupAndWeekNumber(student.studentGroup, 12)>>Collections.emptyList()
        when:
        service.findScheduleForStudent(getRequest())

        then:
        UniversityException exception = thrown()
        exception.message=="Wystąpił nieoczekiwany błąd systemu. Nie znaleziono rozkładu dla podanych parametrów :  Karol Sidor 12"
    }
}

package pl.sidor.ManageUniversity.student.service

import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate

class StudentServiceImplTest extends spock.lang.Specification {

    private StudentRepo studentRepo
    private StudentServiceImpl service
    private CheckUniqeStudentPredicate studentValidator

    void setup() {
        studentRepo = Mock(StudentRepo.class)
        studentValidator = Mock(CheckUniqeStudentPredicate.class)
        service = new StudentServiceImpl(studentRepo, studentValidator)
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

        studentValidator.test(student) >> true
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
        Student actualStudent = service.create(null)

        then:
        actualStudent == student
    }




    def "should throw StudentException"() {

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

        Student studentSecondVersion=Student.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .build()

        studentRepo.save(studentSecondVersion)>>studentSecondVersion
        when:
        service.update(studentSecondVersion)

        then:
        1*studentRepo.save(studentSecondVersion)
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
}

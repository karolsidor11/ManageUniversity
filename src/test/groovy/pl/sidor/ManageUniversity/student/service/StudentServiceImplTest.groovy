package pl.sidor.ManageUniversity.student.service


import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import pl.sidor.ManageUniversity.student.response.StudentResponse
import pl.sidor.ManageUniversity.student.utils.StudentUtils
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate
import spock.lang.Specification

class StudentServiceImplTest extends Specification {

    private StudentRepo studentRepo = Mock(StudentRepo.class)
    private CheckUniqeStudentPredicate studentValidator = Mock(CheckUniqeStudentPredicate.class)

    private StudentServiceImpl service = [studentRepo, studentValidator]

    def "should find student by ID "() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findById(1) >> Optional.of(student)

        when:
        Student actualStudent = service.findById(1).getStudent()

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
        StudentResponse studentResponse = service.findById(id)

        then:
        studentResponse.getError() != null
    }

    def "should create student"() {
        given:
        Student student = StudentUtils.getStudent()
        studentValidator.test(student) >> false
        studentRepo.save(student) >> student

        when:
        Student actualStudent = service.create(student).getStudent()

        then:
        actualStudent == student
        actualStudent.name == "Karol"
        actualStudent.lastName == "Sidor"
    }

    def "should  not create student"() {
        given:
        Student student = StudentUtils.getStudent()
        studentValidator.test(student) >> true
        studentRepo.save(student) >> student

        when:
        StudentResponse studentResponse = service.create(student)

        then:
        studentResponse.error != null
    }

    def "should throw StudentException"() {
        given:
        Student student = StudentUtils.getStudent()
        studentValidator.test(student) >> true
        studentRepo.save(student) >> student

        when:
        StudentResponse studentResponse = service.create(student)

        then:
        studentResponse.error != null
    }

    def "should delete student"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findById(student.id) >> Optional.of(StudentUtils.getStudent())
        studentRepo.deleteById(student.id)

        when:
        service.delete(student.id)

        then:
        1 * studentRepo.deleteById(student.id)
    }

    def "should throw Exception when student id is incorrect"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findById(student.id) >> Optional.empty()

        when:
        StudentResponse studentResponse = service.delete(student.id)

        then:
        studentResponse.error != null
    }

    def "should update student"() {
        given:
        Student student = StudentUtils.getStudent()
        Student studentSecondVersion = StudentUtils.getStudent2()

        when:
        studentRepo.findById(student.id) >> Optional.of(student)
        studentRepo.save(_ as  Student) >> studentSecondVersion
        service.update(studentSecondVersion)

        then:
        0 * studentRepo.findById(student.id)
    }

    def "should throw Exception during update student"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findById(student.id) >> Optional.empty()

        when:
        StudentResponse actualStudent = service.findById(student.id)

        then:
        actualStudent.error != null
    }
}

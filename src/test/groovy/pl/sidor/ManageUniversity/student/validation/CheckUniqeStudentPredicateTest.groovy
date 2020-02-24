package pl.sidor.ManageUniversity.student.validation

import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import pl.sidor.ManageUniversity.student.utils.StudentUtils
import spock.lang.Specification

class CheckUniqeStudentPredicateTest extends Specification {

    private StudentRepo studentRepo = Stub(StudentRepo.class)
    private CheckUniqeStudentPredicate studentValidator = [studentRepo]

    def "should return false when email is uqnige"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findByEmail("jan@wp.pl") >> Collections.emptyList()
        studentRepo.findByPhoneNumber(42323) >> Collections.emptyList()

        when:
        boolean result = studentValidator.test(student)

        then:
        !result
    }

    def "should return false when email and phoneNumber is  uqnige"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findByEmail("karolsidor12@wp.pl") >> Arrays.asList(student)
        studentRepo.findByPhoneNumber(997) >> Arrays.asList(student)

        when:
        boolean result = studentValidator.test(student)

        then:
        !result
    }

    def "should return false when phoneNumber is  not uniqe"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findByEmail("jan@wp.pl" as String) >> Collections.emptyList()
        studentRepo.findByPhoneNumber(500600301) >> Arrays.asList(student)

        when:
        boolean result = studentValidator.test(student)

        then:
        !result
    }

    def "should return true when  phoneNumber is unige"() {
        given:
        Student student = StudentUtils.getStudent()
        studentRepo.findByPhoneNumber(500600301) >> Collections.emptyList()
        studentRepo.findByEmail(_ as String) >> Collections.emptyList()

        when:
        boolean result = !studentValidator.test(student)

        then:
        result
    }
}

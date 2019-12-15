package pl.sidor.ManageUniversity.student.validation

import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import spock.lang.Specification

class CheckUniqeStudentPredicateTest extends Specification {

    private StudentRepo studentRepo = Mock(StudentRepo.class)
    private CheckUniqeStudentPredicate studentValidator = [studentRepo]

    def "should return false when email is uqnige"() {
        given:
        Student student = getStudent()
        studentRepo.findByEmail("jan@wp.pl") >> Collections.emptyList()
        studentRepo.findByPhoneNumber(42323) >> Collections.emptyList()

        when:
        boolean result = studentValidator.test(student)

        then:
        result
    }

    def "should return false when email is  not uqnige"() {
        given:
        Student student =getStudent()
        studentRepo.findByEmail("karolsidor11@wp.pl") >> Arrays.asList(student)
        studentRepo.findByPhoneNumber(997) >> Arrays.asList(student)

        when:
        boolean result = studentValidator.test(student)

        then:
        !result
    }

    def "should return false when phoneNumber is  not uniqe"() {
        given:
        Student student = getStudent()
        studentRepo.findByEmail(_ as String) >> Arrays.asList(student)
        studentRepo.findByPhoneNumber(_ as int) >> Arrays.asList(student)

        when:
        boolean result = studentValidator.test(student)

        then:
        !result
    }

    def "should return true when  phoneNumber is unige"() {
        given:
        Student student = getStudent()
        studentRepo.findByPhoneNumber(500600301) >> Collections.emptyList()
        studentRepo.findByEmail(_ as String) >> Collections.emptyList()

        when:
        boolean result = !studentValidator.test(student)

        then:
        result
    }

    private static Student getStudent() {
        return Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .phoneNumber(4534534)
                .build()
    }
}

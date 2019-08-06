package validation

import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate
import spock.lang.Specification

class CheckUniqeStudentPredicateTest extends Specification {

    private CheckUniqeStudentPredicate studentValidator
    private StudentRepo studentRepo


    void setup() {

        studentRepo = Mock(StudentRepo.class)
        studentValidator = new CheckUniqeStudentPredicate(studentRepo)
    }

    def " should return true when email is uqnige"() {

        given:

        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .phoneNumber(4534534)
                .build()

        studentRepo.findByEmail("jan@wp.pl") >> null
        studentRepo.findByPhoneNumber(42323) >> null

        when:
        boolean result = studentValidator.test(student)

        then:
        result == false
    }

    def " should return false when email is  not uqnige"() {

        given:

        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build()

        studentRepo.findByEmail("karolsidor11@wp.pl") >> student

              when:
        boolean result = studentValidator.test(student)

        then:
        result == false
    }

    def "should return false when phoneNumber is  not uniqe"() {

        given:
        Student student = Student.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .phoneNumber(500600301)
                .build()

        studentRepo.findByEmail(_) >> student
        studentRepo.findByPhoneNumber(_) >> student
        when:
        boolean result = studentValidator.test(student)

        then:
        result == true
    }
}

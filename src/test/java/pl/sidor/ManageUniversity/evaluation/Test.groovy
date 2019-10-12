package evaluation

import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo
import pl.sidor.ManageUniversity.evaluation.service.StudentRatingsCardService
import pl.sidor.ManageUniversity.evaluation.service.StudentRatingsCardServiceImpl
import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import spock.lang.Ignore
import spock.lang.Specification

class Test extends Specification {


    private StudentRatingsCardService service
    private StudentCardRepo studentCardRepo
    private StudentRepo studentRepo

    void setup() {
        studentCardRepo = Mock(StudentCardRepo.class)
        studentRepo=Mock(StudentRepo.class)
        service = new StudentRatingsCardServiceImpl(studentCardRepo,studentRepo)
    }

    @Ignore
    def " test"() {

        given:
        Student student = Student.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .studentRatingsCard(studentRatingsCard)
                .build()

        studentRatingsCard = StudentRatingsCard.builder()
                .id(1L)
                .student(student)
                .build()

        student.setStudentRatingsCard(studentRatingsCard)


       studentCardRepo.findByStudent("Jan","Nowak")>>studentRatingsCard


        when:

        StudentRatingsCard studentRatingsCard = service.findByStudent("Jan", "Nowak")

        then:
        studentRatingsCard == null


    }
}

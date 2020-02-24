package pl.sidor.ManageUniversity.evaluation

import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard
import pl.sidor.ManageUniversity.evaluation.ratingcard.StudentRatingsCardServiceImpl
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo
import pl.sidor.ManageUniversity.exception.UniversityException
import pl.sidor.ManageUniversity.student.model.Student
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import pl.sidor.ManageUniversity.utils.TestStudentData
import spock.lang.Specification

class StudentRatingsCardServiceImplTest extends Specification {

    private StudentRepo studentRepo = Mock(StudentRepo.class)
    private StudentCardRepo studentCardRepo = Mock(StudentCardRepo.class)

    private StudentRatingsCardServiceImpl service = [studentCardRepo, studentRepo]

    def "should  find StudentRatingCard by ID"() {
        given:
        Long id = 1L

        when:
        studentCardRepo.findById(id) >> Optional.of(getStudentRatingCard())
        StudentRatingsCard studentRatingsCard = service.findByID(id)

        then:
        studentRatingsCard != null
        studentRatingsCard.id == 1L
        studentRatingsCard.groups == 2.2
    }

    def "should  throw University Exception"() {
        given:
        Long id = 9999

        when:
        studentCardRepo.findById(id) >> Optional.empty()
        service.findByID(id)

        then:
        thrown(UniversityException.class)
    }

    def "should delete StudentRatingCard by ID"() {
        given:
        Long id = 1L

        when:
        studentCardRepo.findById(id) >> Optional.of(getStudentRatingCard())
        studentCardRepo.deleteById(id) >> {}
        service.deleteCard(id)

        then:
        1 * studentCardRepo.deleteById(id)
    }

    def "should throw University Exception"() {
        given:
        Long id = 999

        when:
        studentCardRepo.findById(id) >> Optional.empty()
        studentCardRepo.deleteById(id) >> {}
        service.deleteCard(id)

        then:
        0 * studentCardRepo.deleteById(id)
    }

    def "should find StudentRatingCard by Student"() {
        given:
        String name = "Karol"
        String lastName = "Sidor"

        when:
        studentRepo.findByNameAndLastName(name, lastName) >> Optional.of(getStudentByNameAndLastName())
        studentCardRepo.findByStudent(getStudentByNameAndLastName().id) >> getStudentRatingCard()
        StudentRatingsCard studentRatingsCard = service.findByStudent(name, lastName)

        then:
        studentRatingsCard != null
    }

    def "should throw University Exception if try find StudentRatingCard"() {
        given:
        String name = "Karol"
        String lastName = "Sidor"

        when:
        studentRepo.findByNameAndLastName(name, lastName) >> Optional.empty()
        service.findByStudent(name, lastName)

        then:
        thrown(UniversityException.class)
    }

    def "should create StudentRatingCard"() {
        given:
        StudentRatingsCard studentRatingsCard = getStudentRatingCard()

        when:
        studentCardRepo.save(studentRatingsCard) >> studentRatingsCard
        StudentRatingsCard savedStudentRatingCard = service.createCard(studentRatingsCard)

        then:
        savedStudentRatingCard != null
        savedStudentRatingCard.id == 1L
    }

    def "should throw University Exception when try save StudentRatingCard"() {
        given:
        StudentRatingsCard studentRatingsCard = null

        when:
        studentRepo.findByNameAndLastName(_ as String, _ as String) >> Optional.empty()
        service.createCard(studentRatingsCard)

        then:
        thrown(UniversityException.class)
    }

    def "should update StudentRatingCard"() {
        given:
        StudentRatingsCard studentRatingsCard = getStudentRatingCard()

        when:
        studentCardRepo.findById(studentRatingsCard.getId()) >> Optional.of(studentRatingsCard)
        studentCardRepo.save(_ as StudentRatingsCard) >>updateStudentRatingCard()

        StudentRatingsCard updateStudentRatingCard = service.updateCard(studentRatingsCard)

        then:
        updateStudentRatingCard!=null
        updateStudentRatingCard.groups==3.2
    }

    private static Student getStudentByNameAndLastName() {
        return TestStudentData.prepareStudent()
    }

    private static StudentRatingsCard getStudentRatingCard() {
        return StudentRatingsCard.builder()
                .id(1L)
                .student(TestStudentData.prepareStudent())
                .groups(2.2)
                .ratingSetList(Collections.emptyList())
                .build()
    }

    private static StudentRatingsCard updateStudentRatingCard() {
        return StudentRatingsCard.builder()
                .id(1L)
                .student(TestStudentData.prepareStudent())
                .groups(3.2)
                .ratingSetList(Collections.emptyList())
                .build()
    }
}

package pl.sidor.ManageUniversity.evaluation.service

import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard
import pl.sidor.ManageUniversity.evaluation.ratingcard.StudentRatingsCardServiceImpl
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo
import pl.sidor.ManageUniversity.evaluation.utils.StudentRatingsCardUtils
import pl.sidor.ManageUniversity.student.repository.StudentRepo
import spock.lang.Specification

class StudentRatingsCardServiceImplTest extends Specification {

    private StudentRepo studentRepo = Mock(StudentRepo.class)
    private StudentCardRepo studentCardRepo = Mock(StudentCardRepo.class)

    private StudentRatingsCardServiceImpl service = [studentCardRepo, studentRepo]

    def "should  find StudentRatingCard by ID"() {
        given:
        Long id = 1L

        when:
        studentCardRepo.findById(id) >> Optional.of(StudentRatingsCardUtils.getStudentRatingCard())
        def response = service.findByID(id)

        then:
        response != null
        response.ratingSet.id == 1L
        response.ratingSet.groups == 2.2
    }

    def "should  throw University Exception"() {
        given:
        Long id = 9999

        when:
        studentCardRepo.findById(id) >> Optional.empty()
        def response = service.findByID(id)

        then:
        response.error != null
    }

    def "should delete StudentRatingCard by ID"() {
        given:
        Long id = 1L

        when:
        studentCardRepo.findById(id) >> Optional.of(StudentRatingsCardUtils.getStudentRatingCard())
        studentCardRepo.deleteById(id) >> {}
        def response = service.deleteCard(id)

        then:
        response.error == null
        noExceptionThrown()
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
        studentRepo.findByNameAndLastName(name, lastName) >> Optional.of(StudentRatingsCardUtils.getStudentByNameAndLastName())
        studentCardRepo.findByStudent(StudentRatingsCardUtils.getStudentByNameAndLastName().id) >> Optional.of(StudentRatingsCardUtils.getStudentRatingCard())
        def response = service.findByStudent(name, lastName)

        then:
        response.ratingSet != null
    }

    def "should throw University Exception if try find StudentRatingCard"() {
        given:
        String name = "Karol"
        String lastName = "Sidor"

        when:
        studentRepo.findByNameAndLastName(name, lastName) >> Optional.empty()
        def response = service.findByStudent(name, lastName)

        then:
        response.ratingSet == null
    }

    def "should create StudentRatingCard"() {
        given:
        StudentRatingsCard studentRatingsCard = StudentRatingsCardUtils.getStudentRatingCard()

        when:
        studentCardRepo.save(studentRatingsCard) >> studentRatingsCard
        StudentRatingsCard savedStudentRatingCard = service.createCard(studentRatingsCard).ratingSet

        then:
        savedStudentRatingCard != null
        savedStudentRatingCard.id == 1L
    }

    def "should throw University Exception when try save StudentRatingCard"() {
        given:
        StudentRatingsCard studentRatingsCard = null

        when:
        studentRepo.findByNameAndLastName(_ as String, _ as String) >> Optional.empty()
        def response = service.createCard(studentRatingsCard)

        then:
        response.error != null
    }

    def "should update StudentRatingCard"() {
        given:
        StudentRatingsCard studentRatingsCard = StudentRatingsCardUtils.getStudentRatingCard()

        when:
        studentCardRepo.findById(studentRatingsCard.getId()) >> Optional.of(studentRatingsCard)
        studentCardRepo.save(_ as StudentRatingsCard) >> StudentRatingsCardUtils.updateStudentRatingCard()

        StudentRatingsCard updateStudentRatingCard = service.updateCard(studentRatingsCard).ratingSet

        then:
        updateStudentRatingCard != null
        updateStudentRatingCard.groups == 3.2
    }
}

package pl.sidor.ManageUniversity.evaluation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;

import java.util.Optional;

@Repository
public interface StudentCardRepo extends CrudRepository<StudentRatingsCard, Long> {

    Optional<StudentRatingsCard> findByStudent(Long student_id);
}

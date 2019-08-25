package pl.sidor.ManageUniversity.evaluation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;

@Repository
public interface StudentCardRepo extends CrudRepository<StudentRatingsCard, Long> {
}

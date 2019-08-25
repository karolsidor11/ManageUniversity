package pl.sidor.ManageUniversity.evaluation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.evaluation.model.RatingSet;

@Repository
public interface RatingRepo extends CrudRepository<RatingSet, Long>{
}

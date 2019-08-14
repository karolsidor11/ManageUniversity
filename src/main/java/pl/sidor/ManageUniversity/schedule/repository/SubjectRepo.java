package pl.sidor.ManageUniversity.schedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.schedule.model.Subject;

@Repository
public interface SubjectRepo extends CrudRepository<Subject, Long> {

}

package pl.sidor.ManageUniversity.recruitment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;

@Repository
public interface CandidateRepo extends CrudRepository<Candidate, Long> {
}

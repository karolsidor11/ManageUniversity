package pl.sidor.ManageUniversity.recruitment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;

import java.util.Optional;

@Repository
public interface RecrutationResultRepo extends CrudRepository<RecrutationResult, Long> {

    Optional<RecrutationResult> findByNameAndLastName(final String name, final String lastName);
}

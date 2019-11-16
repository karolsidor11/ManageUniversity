package pl.sidor.ManageUniversity.recruitment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;

@Repository
public interface RecrutationResultRepo extends CrudRepository<RecrutationResult , Long> {

    RecrutationResult findByNameAndLastName(String name, String lastName);
}

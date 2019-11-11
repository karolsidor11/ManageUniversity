package pl.sidor.ManageUniversity.recruitment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;

import java.util.Optional;

@Repository
public interface PaymentRepo extends CrudRepository<PaymentForStudy, Long> {

    Optional<PaymentForStudy> findByNameAndLastName(String name, String lastName);

}

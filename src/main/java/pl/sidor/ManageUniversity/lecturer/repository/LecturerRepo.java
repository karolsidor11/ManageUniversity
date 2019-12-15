package pl.sidor.ManageUniversity.lecturer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepo extends CrudRepository<Lecturer, Long> {

    List<Lecturer> findByEmail(final String email);

    Optional<Lecturer> findByNameAndLastName(final String name, final String lastName);
}

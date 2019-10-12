package pl.sidor.ManageUniversity.lecturer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import java.util.List;

@Repository
public interface LecturerRepo extends CrudRepository<Lecturer, Long> {

    List<Lecturer> findByEmail(String email);
}

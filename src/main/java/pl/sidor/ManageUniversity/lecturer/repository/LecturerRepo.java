package pl.sidor.ManageUniversity.lecturer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

@Repository
public interface LecturerRepo extends CrudRepository<Lecturer, Long> {
}

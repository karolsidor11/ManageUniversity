package pl.sidor.ManageUniversity.schedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface SubjectRepo extends CrudRepository<Subject, Long> {

    Optional<Subject> findByStartTimeAndEndTime(final LocalTime start, final LocalTime end);

    Optional<Subject> findByLecturer(final Lecturer lecturer);
}

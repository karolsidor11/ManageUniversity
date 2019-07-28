package pl.sidor.ManageUniversity.lecturer.dao;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

@Component
public interface LecturerDao {

    Lecturer findById(long id);

    Lecturer create(Lecturer lecturer);

    void update (Lecturer lecturer);

    void delete(long id);
}

package pl.sidor.ManageUniversity.lecturer.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

@Component
public interface LecturerService {

    Lecturer findById(long id);

    Lecturer create(Lecturer lecturer);

    void update (Lecturer lecturer);

    void delete(long id);
}

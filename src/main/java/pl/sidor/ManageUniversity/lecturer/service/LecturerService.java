package pl.sidor.ManageUniversity.lecturer.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public interface LecturerService {

    Lecturer findById(long id) throws UniversityException;

    Lecturer create(Lecturer lecturer) throws Throwable;

    void update(Lecturer lecturer) throws UniversityException;

    void delete(long id) throws UniversityException;
}

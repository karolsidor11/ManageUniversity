package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.schedule.model.Subject;

@Component
public interface SubjectService {

    Subject getById(Long id) throws UniversityException;

    Subject save(Subject subject) throws Throwable;
}

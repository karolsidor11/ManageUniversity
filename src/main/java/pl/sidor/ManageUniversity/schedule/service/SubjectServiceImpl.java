package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.Objects;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepo subjectRepo;

    @Autowired
    public SubjectServiceImpl(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }


    @Override
    public Subject getById(Long id) {
        return subjectRepo.findById(id).get();
    }

    @Override
    public Subject save(Subject subject) throws UniversityException {
      return  subjectRepo.save(subject);
    }
}

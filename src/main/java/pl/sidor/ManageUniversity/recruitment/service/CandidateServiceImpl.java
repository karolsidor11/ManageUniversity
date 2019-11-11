package pl.sidor.ManageUniversity.recruitment.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo;

import java.util.Objects;

@AllArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private CandidateRepo candidateRepo;

    @Override
    public Candidate create(Candidate candidate) throws UniversityException {
        if (Objects.isNull(candidate)) { throw ExceptionFactory.objectIsEmpty("!!!"); }

        return candidateRepo.save(candidate);
    }

    @Override
    public Candidate findByID(Long id) throws Throwable {

        return candidateRepo.findById(id).orElseThrow(ExceptionFactory.incorrectCandidateID(id));
    }

    @Override
    public void delete(Long id) throws Throwable {
        findByID(id);
        candidateRepo.deleteById(id);
    }
}

package pl.sidor.ManageUniversity.recruitment.service.candidate;

import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;

public interface CandidateService {

    Candidate create(Candidate candidate) throws UniversityException;

    Candidate findByID(Long id) throws Throwable;

    void delete(Long id) throws Throwable;
}

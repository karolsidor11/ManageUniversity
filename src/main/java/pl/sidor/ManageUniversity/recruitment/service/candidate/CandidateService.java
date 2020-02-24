package pl.sidor.ManageUniversity.recruitment.service.candidate;

import pl.sidor.ManageUniversity.recruitment.model.Candidate;

public interface CandidateService {

    Candidate create(final Candidate candidate) throws Throwable;

    Candidate findByID(final Long id) throws Throwable;

    void delete(final Long id) throws Throwable;
}

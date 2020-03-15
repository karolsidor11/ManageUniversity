package pl.sidor.ManageUniversity.recruitment.service.candidate;

import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.response.RecruitmentResponse;

public interface CandidateService {

    RecruitmentResponse create(final Candidate candidate);

    RecruitmentResponse findByID(final Long id);

    RecruitmentResponse delete(final Long id);
}

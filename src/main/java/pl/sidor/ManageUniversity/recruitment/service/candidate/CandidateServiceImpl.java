package pl.sidor.ManageUniversity.recruitment.service.candidate;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;
import pl.sidor.ManageUniversity.recruitment.response.RecruitmentResponse;
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationService;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@Transactional
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final RecrutationService recrutationService;
    private final RecrutationResultRepo resultService;

    @Override
    public RecruitmentResponse create(final Candidate candidate) {
        RecruitmentResponse recruitmentResponse = validateCandidate(candidate);
        return recruitmentResponse.toBuilder().header(Header.getInstance()).candidate(candidate).build();
    }

    @Override
    public RecruitmentResponse findByID(final Long id) {
        return RecruitmentResponse.prepareRecruitmentResponse(candidateRepo.findById(id), ResponseException.pustyObiekt());
    }

    @Override
    public RecruitmentResponse delete(final Long id) {
        RecruitmentResponse response = findByID(id);
        if (Objects.nonNull(response.getError())) {
            return response;
        }
        candidateRepo.deleteById(id);
        return RecruitmentResponse.builder().header(Header.getInstance()).build();
    }

    private RecruitmentResponse validateCandidate(Candidate candidate) {
        if (isNull(candidate) || isNull(candidate.getName()) && isNull(candidate.getLastName())) {
            return RecruitmentResponse.prepareRecruitmentResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        saveMaturaResultIfForCandidate(candidate);
        return RecruitmentResponse.builder().candidate(candidate).build();
    }

    private RecrutationResult saveMaturaResultIfForCandidate(Candidate candidate) {
        double process = recrutationService.process(candidate.getMaturaResults());
        RecrutationResult recrutationResult = prepareRecrutationResult(candidate, process);
        return resultService.save(recrutationResult);
    }

    private RecrutationResult prepareRecrutationResult(final Candidate candidate, final Double result) {
        return RecrutationResult.builder().name(candidate.getName()).lastName(candidate.getLastName()).isAccept(evaluateResult(result)).build();
    }

    private AcceptedInCollage evaluateResult(final Double result) {
        return result < 850 ? AcceptedInCollage.REJECTED : AcceptedInCollage.ACCEPTED;
    }
}

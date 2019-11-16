package pl.sidor.ManageUniversity.recruitment.service.candidate;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationService;

import java.util.Objects;

@AllArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final RecrutationService recrutationService;
    private final RecrutationResultRepo resultService;

    @Override
    public Candidate create(Candidate candidate) throws UniversityException {
        if (Objects.isNull(candidate)) {
            throw ExceptionFactory.objectIsEmpty("!!!");
        }

        Candidate actualCandidate = candidateRepo.save(candidate);
        double process = recrutationService.process(candidate.getMaturaResults());
        RecrutationResult recrutationResult = prepareRecrutationResult(candidate, process);
        resultService.save(recrutationResult);

        return actualCandidate;
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

    private RecrutationResult prepareRecrutationResult(Candidate candidate, double result) {
      return   RecrutationResult.builder()
                .name(candidate.getName())
                .lastName(candidate.getLastName())
                .isAccept(evaluateResult(result))
                .build();
    }

    private AcceptedInCollage evaluateResult(double result) {

        if (result < 320){
            return AcceptedInCollage.REJECTED;
        }
        return AcceptedInCollage.ACCEPTED;
    }
}

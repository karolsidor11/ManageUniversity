package pl.sidor.ManageUniversity.recruitment.service.candidate;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateService;
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationService;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final RecrutationService recrutationService;
    private final RecrutationResultRepo resultService;

    @Override
    public Candidate create( final Candidate candidate) throws Throwable {
        Candidate candidateSaved = ofNullable(candidate).map(candidate1 -> candidateRepo.save(candidate))
                .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));

        double process = recrutationService.process(candidateSaved.getMaturaResults());
        RecrutationResult recrutationResult = prepareRecrutationResult(candidateSaved, process);
        resultService.save(recrutationResult);

        return candidateSaved;
    }

    @Override
    public Candidate findByID(final Long id) throws Throwable {
        return candidateRepo.findById(id).orElseThrow(ExceptionFactory.incorrectCandidateID(id));
    }

    @Override
    public void delete( final Long id) throws Throwable {
        of(findByID(id)).ifPresent(candidate -> candidateRepo.deleteById(id));
    }

    private RecrutationResult prepareRecrutationResult(final Candidate candidate, final Double result) {
        return RecrutationResult.builder()
                .name(candidate.getName())
                .lastName(candidate.getLastName())
                .isAccept(evaluateResult(result))
                .build();
    }

    private AcceptedInCollage evaluateResult(final Double result) {
        if (result < 850) {
            return AcceptedInCollage.REJECTED;
        }
        return AcceptedInCollage.ACCEPTED;
    }
}

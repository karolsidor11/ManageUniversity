package pl.sidor.ManageUniversity.recruitment.service.recrutation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;

import java.util.function.BiFunction;

@Component
@AllArgsConstructor
public class RecrutationResultBuilder implements BiFunction<Double, Candidate, RecrutationResult> {

    private final RecrutationService recrutationService;
    private final RecrutationResultRepo resultService;

    @Override
    public RecrutationResult apply( final Double maturaScore,  final Candidate candidate){
        Double process = null;
        try {
            process = recrutationService.process(candidate.getMaturaResults());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        RecrutationResult recrutationResult = prepareRecrutationResult(candidate, process);
        return resultService.save(recrutationResult);
    }

    private RecrutationResult prepareRecrutationResult( final Candidate candidate, final Double result) {
        return RecrutationResult.builder()
                .name(candidate.getName())
                .lastName(candidate.getLastName())
                .isAccept(evaluateResult(result))
                .build();
    }

    private AcceptedInCollage evaluateResult(final Double result) {
        if (result < 320) {
            return AcceptedInCollage.REJECTED;
        }
        return AcceptedInCollage.ACCEPTED;
    }
}

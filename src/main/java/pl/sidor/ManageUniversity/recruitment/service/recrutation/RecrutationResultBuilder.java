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
    public RecrutationResult apply(Double aDouble, Candidate candidate){


        double process = 0;
        try {
            process = recrutationService.process(candidate.getMaturaResults());
        } catch (UniversityException e) {
            e.printStackTrace();
        }
        RecrutationResult recrutationResult = prepareRecrutationResult(candidate, process);
        resultService.save(recrutationResult);
        return null;
    }

    private RecrutationResult prepareRecrutationResult(Candidate candidate, double result) {
        return RecrutationResult.builder()
                .name(candidate.getName())
                .lastName(candidate.getLastName())
                .isAccept(evaluateResult(result))
                .build();
    }

    private AcceptedInCollage evaluateResult(double result) {

        if (result < 320) {
            return AcceptedInCollage.REJECTED;
        }
        return AcceptedInCollage.ACCEPTED;
    }
}

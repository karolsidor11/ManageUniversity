package pl.sidor.ManageUniversity.recruitment.service.recrutation;

import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.recruitment.model.MaturaResults;

import java.util.Optional;

@Service
public class RecrutationServiceImpl implements RecrutationService {

    private static final double MATH_INDEX = 1.45;
    private static final double POLISH_INDEX = 1.20;
    private static final double LANGUAGE_INDEX = 1.35;

    @Override
    public Double process(final MaturaResults maturaResults) {
        MaturaResults actuaMaturaResults = Optional.ofNullable(maturaResults).orElse(MaturaResults.getDefaultValues());

        Double polishResult = actuaMaturaResults.getPolishResult();
        Double languageResult = actuaMaturaResults.getLanguageResult();
        Double mathResult = actuaMaturaResults.getMathResult();

        return (((polishResult * POLISH_INDEX) + (languageResult * LANGUAGE_INDEX) + (mathResult * MATH_INDEX)) / 3) * 100;
    }
}

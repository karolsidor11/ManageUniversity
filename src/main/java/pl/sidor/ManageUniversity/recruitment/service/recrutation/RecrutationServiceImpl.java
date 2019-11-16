package pl.sidor.ManageUniversity.recruitment.service.recrutation;

import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.recruitment.model.MaturaResults;

@Service
public class RecrutationServiceImpl implements RecrutationService {

    private static final double MATH_INDEX = 1.45;
    private static final double POLISH_INDEX = 1.20;
    private static final double LANGUAGE_INDEX = 1.35;

    @Override
    public double process(MaturaResults maturaResults) {

        Double polishResult = maturaResults.getPolishResult();
        Double languageResult = maturaResults.getLanguageResult();
        Double mathResult = maturaResults.getMathResult();

       double result = (((polishResult * POLISH_INDEX) + (languageResult * LANGUAGE_INDEX)
                + (mathResult * MATH_INDEX)) / 3) * 100;

       return  result;
    }
}

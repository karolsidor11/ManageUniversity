package pl.sidor.ManageUniversity.recruitment.service.recrutation;

import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.MaturaResults;

public interface RecrutationService {

    double process(MaturaResults maturaResults) throws UniversityException;
}

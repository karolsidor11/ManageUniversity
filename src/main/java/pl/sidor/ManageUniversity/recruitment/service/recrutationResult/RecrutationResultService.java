package pl.sidor.ManageUniversity.recruitment.service.recrutationResult;

import pl.sidor.ManageUniversity.recruitment.response.RecrutationResultResponse;

public interface RecrutationResultService {

    RecrutationResultResponse checkRecrutationResult(final String name, final String lastName);
}

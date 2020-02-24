package pl.sidor.ManageUniversity.recruitment.service.recrutationResult;

import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;

public interface RecrutationResultService {

    RecrutationResult checkRecrutationResult(final String name, final String lastName) throws Throwable;
}

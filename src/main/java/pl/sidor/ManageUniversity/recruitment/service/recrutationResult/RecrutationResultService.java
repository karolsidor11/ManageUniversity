package pl.sidor.ManageUniversity.recruitment.service.recrutationResult;

import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;

public interface RecrutationResultService {

    RecrutationResult checkRecrutationResult(String name, String lastName) throws Throwable;
}

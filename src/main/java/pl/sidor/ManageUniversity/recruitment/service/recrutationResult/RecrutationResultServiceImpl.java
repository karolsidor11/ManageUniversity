package pl.sidor.ManageUniversity.recruitment.service.recrutationResult;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;
import pl.sidor.ManageUniversity.recruitment.response.RecrutationResultResponse;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RecrutationResultServiceImpl implements RecrutationResultService {

    private RecrutationResultRepo recrutationResultRepo;

    @Override
    public RecrutationResultResponse checkRecrutationResult(final String name, final String lastName) {
        Optional<RecrutationResult> recrutationResult = recrutationResultRepo.findByNameAndLastName(name, lastName);
        return RecrutationResultResponse.prepareRecrutationResultResponse(recrutationResult, ResponseException.pustyObiekt());
    }
}

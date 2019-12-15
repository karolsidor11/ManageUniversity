package pl.sidor.ManageUniversity.recruitment.service.recrutationResult;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
public class RecrutationResultServiceImpl implements RecrutationResultService {

    private RecrutationResultRepo recrutationResultRepo;

    @Override
    public RecrutationResult checkRecrutationResult(final String name, final String lastName) throws Throwable {
        return ofNullable(recrutationResultRepo.findByNameAndLastName(name, lastName))
                .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }
}

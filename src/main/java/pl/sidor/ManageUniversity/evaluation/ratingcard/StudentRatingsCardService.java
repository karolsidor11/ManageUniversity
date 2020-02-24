package pl.sidor.ManageUniversity.evaluation.ratingcard;

import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.evaluation.response.StudentRatingsCardResponse;

public interface StudentRatingsCardService {

    StudentRatingsCardResponse findByID(final Long id);

    StudentRatingsCardResponse findByStudent(final String name, final String lastName);

    StudentRatingsCardResponse createCard(final StudentRatingsCard studentRatingsCard);

    StudentRatingsCardResponse updateCard(final StudentRatingsCard studentRatingsCard);

    StudentRatingsCardResponse deleteCard(final Long id);
}

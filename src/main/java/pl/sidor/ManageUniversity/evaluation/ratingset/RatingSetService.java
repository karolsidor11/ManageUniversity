package pl.sidor.ManageUniversity.evaluation.ratingset;

import pl.sidor.ManageUniversity.evaluation.model.RatingSet;
import pl.sidor.ManageUniversity.evaluation.response.RatingSetResponse;

public interface RatingSetService {

    RatingSetResponse findById(final Long id);

    RatingSetResponse create(final RatingSet ratingSet);

    RatingSetResponse update(final RatingSet ratingSet);

    RatingSetResponse delete(final Long id);
}

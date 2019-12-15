package pl.sidor.ManageUniversity.evaluation.ratingset;

import pl.sidor.ManageUniversity.evaluation.model.RatingSet;

public interface RatingSetService {

    RatingSet findById(final Long id) throws Throwable;

    RatingSet create(final RatingSet ratingSet) throws Throwable;

    RatingSet update(final RatingSet ratingSet) throws Throwable;

    boolean delete(final Long id) throws Throwable;
}

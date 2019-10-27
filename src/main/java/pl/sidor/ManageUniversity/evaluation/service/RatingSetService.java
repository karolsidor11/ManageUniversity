package pl.sidor.ManageUniversity.evaluation.service;

import pl.sidor.ManageUniversity.evaluation.model.RatingSet;

public interface RatingSetService {

    RatingSet findById(Long id) throws Throwable;

    RatingSet create(RatingSet ratingSet) throws Throwable;

    RatingSet update(RatingSet ratingSet) throws Throwable;

    boolean delete(Long id) throws Throwable;
}

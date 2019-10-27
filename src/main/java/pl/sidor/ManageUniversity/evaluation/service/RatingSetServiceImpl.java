package pl.sidor.ManageUniversity.evaluation.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.evaluation.model.RatingSet;
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;

import java.util.Objects;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
public class RatingSetServiceImpl implements RatingSetService {

    private RatingRepo ratingRepo;

    @Override
    public RatingSet findById(Long id) throws Throwable {
        return ratingRepo.findById(id).orElseThrow(ExceptionFactory.inncorectRatingSetID(String.valueOf(id)));
    }

    @Override
    public RatingSet create(RatingSet ratingSet) throws Throwable {

        if(Objects.isNull(ratingSet)){
            throw ExceptionFactory.objectIsEmpty("!!!");
        }
        return ratingRepo.save(ratingSet);
    }

    @Override
    public RatingSet update(RatingSet ratingSet) throws Throwable {

        RatingSet ratingSet3 = of(findById(ratingSet.getId())).
                map(ratingSet2 -> builder(ratingSet2, ratingSet))
                .orElseThrow(ExceptionFactory.objectIsEmpty(ratingSet.toString()));

        return ratingRepo.save(ratingSet3);
    }

    @Override
    public boolean delete(Long id) throws Throwable {
        of(findById(id)).ifPresent(ratingSet -> ratingRepo.deleteById(id));
        return true;
    }

    private RatingSet builder(RatingSet oldRatingSet, RatingSet updateRatingSet) {
        oldRatingSet.setId(updateRatingSet.getId());
        oldRatingSet.setLecturer(updateRatingSet.getLecturer());
        oldRatingSet.setSubject(updateRatingSet.getSubject());
        oldRatingSet.setRating(updateRatingSet.getRating());
        return updateRatingSet;
    }
}

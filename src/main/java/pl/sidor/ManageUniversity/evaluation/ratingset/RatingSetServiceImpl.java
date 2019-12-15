package pl.sidor.ManageUniversity.evaluation.ratingset;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.evaluation.model.RatingSet;
import pl.sidor.ManageUniversity.evaluation.ratingset.RatingSetService;
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
public class RatingSetServiceImpl implements RatingSetService {

    private final RatingRepo ratingRepo;

    @Override
    public RatingSet findById(final Long id) throws Throwable {
        return ratingRepo.findById(id).orElseThrow(ExceptionFactory.inncorectRatingSetID(String.valueOf(id)));
    }

    @Override
    public RatingSet create(RatingSet ratingSet) throws Throwable {
        return ofNullable(ratingSet).map(ratingSet1 -> ratingRepo.save(ratingSet))
                .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public RatingSet update(RatingSet ratingSet) throws Throwable {
        RatingSet ratingSet3 = ofNullable(findById(ratingSet.getId()))
                .map(ratingSet2 -> builder(ratingSet2, ratingSet))
                .orElseThrow(ExceptionFactory.objectIsEmpty(ratingSet.toString()));

        return ratingRepo.save(ratingSet3);
    }

    @Override
    public boolean delete(final Long id) throws Throwable {
        of(findById(id)).ifPresent(ratingSet -> ratingRepo.deleteById(id));
        return true;
    }

    private RatingSet builder(final RatingSet oldRatingSet, final RatingSet updateRatingSet) {
        oldRatingSet.setId(updateRatingSet.getId());
        oldRatingSet.setLecturer(updateRatingSet.getLecturer());
        oldRatingSet.setSubject(updateRatingSet.getSubject());
        oldRatingSet.setRating(updateRatingSet.getRating());
        return updateRatingSet;
    }
}

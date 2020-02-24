package pl.sidor.ManageUniversity.evaluation.ratingset;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.evaluation.model.RatingSet;
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo;
import pl.sidor.ManageUniversity.evaluation.response.RatingSetResponse;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.of;

@Transactional
@AllArgsConstructor
public class RatingSetServiceImpl implements RatingSetService {

    private final RatingRepo ratingRepo;

    @Override
    public RatingSetResponse findById(final Long id) {
        Optional<RatingSet> ratingRepoById = ratingRepo.findById(id);
        return RatingSetResponse.prepareStudentRatingCardResponse(ratingRepoById, ResponseException.pustyObiekt());
    }

    @Override
    public RatingSetResponse create(RatingSet ratingSet) {
        Optional<RatingSet> ratingSet2 = validateRatingSet(ratingSet);
        return RatingSetResponse.prepareStudentRatingCardResponse(ratingSet2, ResponseException.pustyObiekt());
    }

    @Override
    public RatingSetResponse update(RatingSet ratingSet) {
        RatingSetResponse ratingSetResponse = findById(ratingSet.getId());
        if (ratingSetResponse.getError() != null) {
            return ratingSetResponse;
        }
        RatingSet builder = builder(ratingSetResponse.getRatingSet(), ratingSet);
        Optional<RatingSet> save = of(ratingRepo.save(builder));
        return RatingSetResponse.prepareStudentRatingCardResponse(save, ResponseException.pustyObiekt());
    }

    @Override
    public RatingSetResponse delete(final Long id) {
        RatingSetResponse ratingSetResponse = findById(id);
        if (ratingSetResponse.getError() != null) {
            return ratingSetResponse;
        }
        ratingRepo.deleteById(id);
        return RatingSetResponse.builder().header(Header.getInstance()).build();
    }

    private Optional<RatingSet> validateRatingSet(RatingSet ratingSet) {
        if (Objects.isNull(ratingSet)) {
            return Optional.empty();
        }
        return Optional.of(ratingSet);
    }

    private RatingSet builder(final RatingSet oldRatingSet, final RatingSet updateRatingSet) {
        oldRatingSet.setId(updateRatingSet.getId());
        oldRatingSet.setLecturer(updateRatingSet.getLecturer());
        oldRatingSet.setSubject(updateRatingSet.getSubject());
        oldRatingSet.setRating(updateRatingSet.getRating());
        return updateRatingSet;
    }
}

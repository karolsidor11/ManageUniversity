package pl.sidor.ManageUniversity.evaluation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.sidor.ManageUniversity.evaluation.model.RatingSet;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;

import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
public class RatingSetResponse {

    private Header header;
    private RatingSet ratingSet;
    private Error error;

    public static RatingSetResponse prepareStudentRatingCardResponse(Optional<RatingSet> ratingSet, Error error) {
        return ratingSet.isPresent() ? buildSuccessResponse(ratingSet) : buildErrorResponse(error);
    }

    private static RatingSetResponse buildErrorResponse(Error error) {
        return RatingSetResponse.builder().header(Header.getInstance()).error(error).build();
    }

    private static RatingSetResponse buildSuccessResponse(Optional<RatingSet> ratingSet) {
        return RatingSetResponse.builder().header(Header.getInstance()).ratingSet(ratingSet.get()).build();
    }
}

package pl.sidor.ManageUniversity.evaluation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;

import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
public class StudentRatingsCardResponse {

    private Header header;
    private StudentRatingsCard ratingSet;
    private Error error;

    public static StudentRatingsCardResponse prepareStudentRatingCardResponse(Optional<StudentRatingsCard> ratingSet, Error error) {
        return ratingSet.isPresent() ? buildSuccessResponse(ratingSet) : buildErrorResponse(error);
    }

    private static StudentRatingsCardResponse buildErrorResponse(Error error) {
        return StudentRatingsCardResponse.builder().header(Header.getInstance()).error(error).build();
    }

    private static StudentRatingsCardResponse buildSuccessResponse(Optional<StudentRatingsCard> ratingSet) {
        return StudentRatingsCardResponse.builder().header(Header.getInstance()).ratingSet(ratingSet.get()).build();
    }
}

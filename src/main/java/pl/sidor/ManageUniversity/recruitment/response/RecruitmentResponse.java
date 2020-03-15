package pl.sidor.ManageUniversity.recruitment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;

import java.util.Optional;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class RecruitmentResponse {

    private Header header;
    private Candidate candidate;
    private Error error;

    public static RecruitmentResponse prepareRecruitmentResponse(Optional<Candidate> candidate, Error error) {
        return candidate.isPresent() ? buildSuccessResponse(candidate.get()) : buildErrorResponse(error);
    }

    private static RecruitmentResponse buildSuccessResponse(Candidate candidate) {
        return RecruitmentResponse.builder().header(Header.getInstance()).candidate(candidate).build();
    }

    private static RecruitmentResponse buildErrorResponse(Error error) {
        return RecruitmentResponse.builder().header(Header.getInstance()).error(error).build();
    }
}

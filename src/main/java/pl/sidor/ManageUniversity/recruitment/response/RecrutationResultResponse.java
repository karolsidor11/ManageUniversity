package pl.sidor.ManageUniversity.recruitment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.recruitment.model.RecrutationResult;

import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
public class RecrutationResultResponse {

    private Header header;
    private RecrutationResult recrutationResult;
    private Error error;

    public static RecrutationResultResponse prepareRecrutationResultResponse(Optional<RecrutationResult> result, Error error) {
        return result.isPresent() ? buildSuccessResponse(result.get()) : buildErrorResponse(error);
    }

    private static RecrutationResultResponse buildSuccessResponse(RecrutationResult recrutationResult) {
        return RecrutationResultResponse.builder().header(Header.getInstance()).recrutationResult(recrutationResult).build();
    }

    private static RecrutationResultResponse buildErrorResponse(Error error) {
        return RecrutationResultResponse.builder().header(Header.getInstance()).error(error).build();
    }
}

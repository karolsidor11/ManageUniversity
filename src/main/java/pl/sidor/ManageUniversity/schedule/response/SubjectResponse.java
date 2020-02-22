package pl.sidor.ManageUniversity.schedule.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {

    private Header header;
    private Subject subject;
    private Error error;

    public static SubjectResponse prepareResponse(Optional<Subject> subject, Error error) {
        return subject.isPresent() ? buildSuccessResponse(subject.get()) : buildErrorResponse(error);
    }

    private static SubjectResponse buildSuccessResponse(Subject subject) {
        return SubjectResponse.builder().header(Header.getInstance()).subject(subject).build();
    }

    private static SubjectResponse buildErrorResponse(Error error) {
        return SubjectResponse.builder().header(Header.getInstance()).error(error).build();
    }
}

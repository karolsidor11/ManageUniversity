package pl.sidor.ManageUniversity.schedule.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.schedule.model.Subject;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {

    private Header header;
    private Subject subject;
    private Error error;

    public static SubjectResponse prepareSubjectResponse(Subject subject) {
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setHeader(Header.getInstance());
        subjectResponse.setSubject(subject);
        return subjectResponse;
    }

    public static SubjectResponse prepareSubjectResponse(Error errors) {
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setHeader(Header.getInstance());
        subjectResponse.setError(errors);
        return subjectResponse;
    }
}

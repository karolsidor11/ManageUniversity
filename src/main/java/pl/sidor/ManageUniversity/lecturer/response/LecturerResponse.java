package pl.sidor.ManageUniversity.lecturer.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerResponse {

    private Header header;
    private Lecturer lecturer;
    private Error error;

    public static LecturerResponse prepareLecturerResponse(Lecturer lecturer) {
        LecturerResponse lecturerResponse = new LecturerResponse();
        lecturerResponse.setHeader(Header.getHeader());
        lecturerResponse.setLecturer(lecturer);
        return lecturerResponse;
    }

    public static LecturerResponse prepareLecturerResponse(Error errors) {
        LecturerResponse lecturerResponse = new LecturerResponse();
        lecturerResponse.setHeader(Header.getHeader());
        lecturerResponse.setError(errors);
        return lecturerResponse;
    }
}

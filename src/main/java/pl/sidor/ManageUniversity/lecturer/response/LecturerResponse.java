package pl.sidor.ManageUniversity.lecturer.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerResponse {

    private Header header;
    private Lecturer lecturer;
    private Error error;

    public static LecturerResponse prepareLecturerResponse(Optional<Lecturer> lecturer, Error errors) {
        return lecturer.isPresent() ? buildSuccessResponse(lecturer):buildErrorResponse(errors);
    }

    private static LecturerResponse buildSuccessResponse(Optional<Lecturer> lecturer) {
        return LecturerResponse.builder().header(Header.getInstance()).lecturer(lecturer.get()).build();
    }

    private static LecturerResponse buildErrorResponse(Error errors) {
        return LecturerResponse.builder().header(Header.getInstance()).error(errors).build();
    }
}

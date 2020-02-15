package pl.sidor.ManageUniversity.student.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.student.model.Student;

import java.util.Optional;


@Builder
@Getter
@AllArgsConstructor
public class StudentResponse {

    private Header header;
    private Student student;
    private Error error;

    public static StudentResponse prepareStudentResponse(Optional<Student> student, Error error) {
        return student.isPresent() ? buildSuccessResponse(student) : buildErrorResponse(error);
    }

    private static StudentResponse buildErrorResponse(Error error) {
        return StudentResponse.builder().header(Header.getInstance()).error(error).build();
    }

    private static StudentResponse buildSuccessResponse(Optional<Student> student) {
        return StudentResponse.builder().header(Header.getInstance()).student(student.get()).build();
    }
}

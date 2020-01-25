package pl.sidor.ManageUniversity.student.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.student.model.Student;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Header header;
    private Student student;
    private Error error;

    public static StudentResponse prepareStudentResponse(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setHeader(Header.getHeader());
        studentResponse.setStudent(student);
        return studentResponse;
    }

    public static StudentResponse prepareStudentResponse(Error errors) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setHeader(Header.getHeader());
        studentResponse.setError(errors);
        return studentResponse;
    }
}

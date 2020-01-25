package pl.sidor.ManageUniversity.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.response.StudentResponse;

@Slf4j
@Getter
public class ResponseException {

    public static StudentResponse brakStudenta(Long id){
        return StudentResponse.prepareStudentResponse(Error.createError(MessageException.W_BAZIE_BRAK_STUDENTA,
                ExceptionFactory.incorrectStudentID(id).getMessage()));
    }

    public static StudentResponse brakStudenta( String name,  String lastName){
        return StudentResponse.prepareStudentResponse(Error.createError(MessageException.W_BAZIE_BRAK_STUDENTA,
                ExceptionFactory.incorectStudentName(name, lastName).getMessage()));
    }

    public static StudentResponse istniejeStudent(Student student){
         return StudentResponse.prepareStudentResponse(Error.createError(MessageException.W_BAZIE_ISTNIEJE_STUDENT,
                ExceptionFactory.studentInDatabase(student.getEmail()).getMessage()));
    }
}

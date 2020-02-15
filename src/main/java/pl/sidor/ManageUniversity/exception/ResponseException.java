package pl.sidor.ManageUniversity.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.sidor.ManageUniversity.schedule.response.ScheduleResponse;
import pl.sidor.ManageUniversity.schedule.response.SubjectResponse;
import pl.sidor.ManageUniversity.student.model.Student;

@Slf4j
@Getter
public class ResponseException {

    public static Error brakStudenta(Long id){
        return Error.createError(MessageException.W_BAZIE_BRAK_STUDENTA,
                ExceptionFactory.incorrectStudentID(id).getMessage());
    }

    public static Error brakStudenta( String name,  String lastName){
        return Error.createError(MessageException.W_BAZIE_BRAK_STUDENTA,
                ExceptionFactory.incorectStudentName(name, lastName).getMessage());
    }

    public static Error istniejeStudent(Student student){
         return Error.createError(MessageException.W_BAZIE_ISTNIEJE_STUDENT,
                ExceptionFactory.studentInDatabase(student.getEmail()).getMessage());
    }

    public static Error brakWykladowcy(Long id){
        return Error.createError(MessageException.W_BAZIE_BRAK_WYKLADOWCA,
                ExceptionFactory.incorrectLecturerID(id).getMessage());
    }

    public static  Error pustyObiekt(){
        return Error.createError(MessageException.OBJECT_IS_EMPTY,
                ExceptionFactory.objectIsEmpty("!!!").getMessage());
    }

    public static SubjectResponse brakPrzedmiotu(Long id){
        return  SubjectResponse.prepareSubjectResponse(Error.createError(MessageException.W_BAZIE_BRAK_PLANU,
                ExceptionFactory.incorrectSubjectID(String.valueOf(id)).getMessage()));
    }

    public  static  SubjectResponse istniejePrzedmiot(){
        return SubjectResponse.prepareSubjectResponse(Error.createError(MessageException.W_BAZIE_ISTNIEJE_PRZEDMIOT_W_CZASIE,
                ExceptionFactory.incorrectTime("!!!").getMessage()));
    }

    public static  SubjectResponse brakPrzedmiotuDlaWykladowcy(){
        return SubjectResponse.prepareSubjectResponse(Error.createError(MessageException.OBJECT_IS_EMPTY,
                ExceptionFactory.objectIsEmpty("Brak  przedmiotów  dla wykładowcy.").getMessage()));
    }

    public static ScheduleResponse brakRozkladu(){
        return ScheduleResponse.prepareScheduleResponse(Error.createError(MessageException.OBJECT_IS_EMPTY,
                ExceptionFactory.objectIsEmpty("Brak rozkładu").getMessage()));
    }

    public static ScheduleResponse istniejeRozklad(){
        return ScheduleResponse.prepareScheduleResponse(Error.createError(MessageException.OBJECT_IS_EMPTY,
                ExceptionFactory.objectIsEmpty("W bazie  istnieje rozkład.").getMessage()));
    }
}

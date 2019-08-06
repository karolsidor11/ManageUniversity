package pl.sidor.ManageUniversity.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class ExceptionFactory {

    public  static UniversityException studentInDatabase(){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_STUDENT.message);
    }
    public static  UniversityException lecturerInDatabase(){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_WYKLADOWCA.message);
    }

    public static  UniversityException incorrectStudentID(){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_STUDENT.message);
    }
    public static  UniversityException incorrectLecturerID(){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_WYKLADOWCA.message);
    }
}

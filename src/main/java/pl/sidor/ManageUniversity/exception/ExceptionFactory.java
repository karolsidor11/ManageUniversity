package pl.sidor.ManageUniversity.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class ExceptionFactory {

    public  static UniversityException studentInDatabase(String details){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_STUDENT.message+":"+details);
    }
    public static  UniversityException lecturerInDatabase(String details){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_WYKLADOWCA.message+":"+details);
    }

    public static  UniversityException incorrectStudentID(String details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_STUDENTA.message+":"+details);
    }
    public static  UniversityException incorrectLecturerID(String details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_WYKLADOWCA.message+":"+details);
    }

    public static UniversityException objectIsEmpty(String details){
        return  new UniversityException(MessageException.OBJECT_IS_EMPTY.message+":"+details);
    }
    public static UniversityException incorrectSubjectID(String details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_PRZEDMIOTU+":"+details);
    }

    public  static  UniversityException incorrectTime(String details){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_PRZEDMIOT_W_CZASIE+":"+details);
    }

    public static  UniversityException incorrectScheduleID(String  details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_PLANU+":"+details);
    }

     public static  UniversityException incorrectPlanDay(String details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_PLANU_O_PODANYM_DNIU+":"+details);
     }
}

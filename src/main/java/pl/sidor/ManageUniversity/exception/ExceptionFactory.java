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

    public static  UniversityException incorrectStudentID(Long details){
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

     public static UniversityException inncorectRatingSetID(String details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_ZESTAW_OCEN+":"+details);
     }

     public  static  UniversityException incorrectStudentCardID(String details){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_KARTY_STUDENTA+":"+details);
     }

     public static UniversityException incorectStudentName(String name, String lastName){
        return  new UniversityException(MessageException.W_BAZIE_BRAK_STUDENTA_O_DANYCH.message+": "+name+" "+lastName);
     }

     public  static UniversityException incorectScheduleDay(String details){
        return  new UniversityException(MessageException.W_BAZIE_ISTNIEJE_ROZKLAD.message+": "+details);
     }

    public  static UniversityException incorectScheduleWeek(Integer details){
        return  new UniversityException(MessageException.W_BAZIE_NIE_ISTNIEJE_ROZKLAD.message+": "+details);
    }

    public static UniversityException nieoczekianyBladSystemu(String name, String lastName, Integer weekNumber){
        return  new UniversityException(MessageException.NIEOCZEKIWANY_BLAD_SYSTEMU.message+" "+name+" "+lastName+ " "+weekNumber);
    }

    public static UniversityException incorrectCandidateID(Long id){
        return new UniversityException(MessageException.W_BAZIE_NIE_ISTNIEJE_KANDYDAT.message+" "+id);
    }
}

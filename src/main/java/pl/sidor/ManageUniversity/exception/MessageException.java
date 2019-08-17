package pl.sidor.ManageUniversity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageException {

    W_BAZIE_ISTNIEJE_STUDENT("W bazie istnieje STUDENT o podanym adresie email." ),
    W_BAZIE_ISTNIEJE_WYKLADOWCA("W bazie istnieje WYKŁADOWCA o podanym adresie email."),
    W_BAZIE_BRAK_STUDENTA("W bazie nie istnieje STUDENT o podanym id."),
    W_BAZIE_BRAK_WYKLADOWCA("W bazie nie istnieje WYKŁADOWCA o podanym id."),
    W_BAZIE_BRAK_PLANU("W bazie nie istnieje PLAN ZAJEĆ o podanym id."),
    W_BAZIE_BRAK_PLANU_O_PODANYM_DNIU("W bazie brak PLANU w podany dzień tygodnia."),
    W_BAZIE_BRAK_PRZEDMIOTU("W bazie nie istnieje PRZEDMIOT o podanym id."),
    W_BAZIE_ISTNIEJE_PRZEDMIOT_W_CZASIE("W bazie  istnieje przedmiot w czasie "),
    OBJECT_IS_EMPTY("Przekazywany obiekt jest pusty.");

    public final String message;

}

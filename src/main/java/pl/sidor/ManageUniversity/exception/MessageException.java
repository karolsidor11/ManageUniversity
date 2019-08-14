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
    OBJECT_IS_EMPTY("Przekazywany obiekt jest pusty.");

    public final String message;

}

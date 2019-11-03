package pl.sidor.ManageUniversity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageException {

    W_BAZIE_ISTNIEJE_STUDENT("W bazie istnieje STUDENT o podanym adresie email." ),
    W_BAZIE_ISTNIEJE_WYKLADOWCA("W bazie istnieje WYKŁADOWCA o podanym adresie email."),
    W_BAZIE_BRAK_STUDENTA("W bazie nie istnieje STUDENT o podanym id."),
    W_BAZIE_BRAK_STUDENTA_O_DANYCH("W bazie nie istnieje STUDENT o podanym imieniu i nazwisku."),
    W_BAZIE_BRAK_WYKLADOWCA("W bazie nie istnieje WYKŁADOWCA o podanym id."),
    W_BAZIE_BRAK_PLANU("W bazie nie istnieje PLAN ZAJEĆ o podanym id."),
    W_BAZIE_BRAK_PLANU_O_PODANYM_DNIU("W bazie brak PLANU w podany dzień tygodnia."),
    W_BAZIE_BRAK_PRZEDMIOTU("W bazie nie istnieje PRZEDMIOT o podanym id."),
    W_BAZIE_ISTNIEJE_PRZEDMIOT_W_CZASIE("W bazie  istnieje przedmiot w czasie. "),
    W_BAZIE_BRAK_ZESTAW_OCEN("W bazie nie istnieje arkusz ocen o podanym id."),
    W_BAZIE_BRAK_KARTY_STUDENTA("W bazie nie istnieje karta studenta o podanym id."),
    W_BAZIE_ISTNIEJE_ROZKLAD("W bazie istnieje rozkład o podanym dniu tygodnia."),
    W_BAZIE_NIE_ISTNIEJE_ROZKLAD("W bazie istnieje rozkład o podanym numerze tygodnia."),
    W_BAZIE_NIE_ISTNIEJE_KANDYDAT("W bazie  nie istnieje kandydat na studia o podanym identyfikatorze."),
    OBJECT_IS_EMPTY("Przekazywany obiekt jest pusty."),
    NIEOCZEKIWANY_BLAD_SYSTEMU("Wystąpił nieoczekiwany błąd systemu. Nie znaleziono rozkładu dla podanych parametrów : ");

    public final String message;
}

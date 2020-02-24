package pl.sidor.ManageUniversity.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Days {
    Poniedzialek ("Poniedziałek"),
    Wtorek("Wtorek"),
    Sroda("Sroda"),
    Czwartek("Czwartek"),
    Piatek("Piątek");

    private final String day;
}

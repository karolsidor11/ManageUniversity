package pl.sidor.ManageUniversity.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Days {
    Poniedziałek ("Poniedziałek"),
    Wtorek("Wtorek"),
    Środa("Środa"),
    Czwartek("Czwartek"),
    Piątek("Piątek");

    private final String day;
}

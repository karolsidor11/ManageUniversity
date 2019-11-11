package pl.sidor.ManageUniversity.recruitment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudyDirectionPayments {

    INFORMATYKA("Informatyka", 3000),
    MATEMATYKA("Matematyka", 2500),
    ELEKTROTECHNIKA("Elektrotechnika", 2500),
    INZYNIERIA_SRODOWISKA("Inżynieria Środowiska", 2000),
    MECHATRONIKA("Mechatronika", 2800),
    BUDOWNICTWO ("Budownictwo", 2600),
    ARCHITEKTURA("Architektura", 2500),
    TRANSPORT("Transport", 1800);

    private final String studyDirection;
    private final double price;
}

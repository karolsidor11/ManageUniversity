package pl.sidor.ManageUniversity.recruitment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudyDirection {

    INFORMATYKA("Informatyka"),
    MATEMATYKA("Matematyka"),
    ELEKTROTECHNIKA("Elektrotechnika"),
    INZYNIERIA_SRODOWISKA("Inżynieria Środowiska"),
    MECHATRONIKA("Mechatronika"),
    BUDOWNICTWO ("Budownictwo"),
    ARCHITEKTURA("Architektura"),
    TRANSPORT("Transport");

    private final String studyDirection;

}

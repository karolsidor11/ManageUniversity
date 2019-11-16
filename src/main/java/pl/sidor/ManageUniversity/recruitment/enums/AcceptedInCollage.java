package pl.sidor.ManageUniversity.recruitment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AcceptedInCollage {

    ACCEPTED("PRZYJĘTY"),
    REJECTED("ODRZUCONY");

    private final String accpeted;
}

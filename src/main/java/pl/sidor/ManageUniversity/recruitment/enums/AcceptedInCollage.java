package pl.sidor.ManageUniversity.recruitment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptedInCollage {

    ACCEPTED("PRZYJĘTY"),
    REJECTED("ODRZUCONY");

    private final String isAccpeted;
}

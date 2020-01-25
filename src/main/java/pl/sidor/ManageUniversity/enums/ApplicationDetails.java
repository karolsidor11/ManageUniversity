package pl.sidor.ManageUniversity.enums;

import lombok.Getter;

@Getter
public enum ApplicationDetails {

    NAME("Manage University"),
    VERSION("1.0");

    ApplicationDetails(String description) {
        this.description = description;
    }

    private String description;
}

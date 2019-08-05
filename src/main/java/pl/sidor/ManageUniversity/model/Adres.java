package pl.sidor.ManageUniversity.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
@Data
@Builder
public class Adres {

    @Column(name = "City")
    @NotNull(message = "Nazwa miasta nie może być null.")
    private String city;

    @Column(name = "Street")
    @NotNull(message = "Nazwa ulicy nie może być null.")
    private String streetAdress;

    @Column(name = "ZipCode")
    @NotNull(message = "Kod pocztowy nie może być null.")
    private String zipCode;

    @Column(name = "HouseNumber")
    @Size(min = 1, max = 999)
    private int houseNumber;
}

package pl.sidor.ManageUniversity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Adres implements Serializable {

    private static final long serialVersionUID = -1939151550153570370L;

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

package pl.sidor.ManageUniversity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
 public class Adres {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Miasto")
    private String city;

    @Column(name = "Ulica")
    private String streetAdress;

    @Column(name = "Kod_pocztowy")
    private String zipCode;

    @Column(name = "Numer_domu")
    private int houseNumber;
}

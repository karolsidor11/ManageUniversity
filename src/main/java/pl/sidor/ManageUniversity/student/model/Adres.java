package pl.sidor.ManageUniversity.student.model;

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
class Adres {

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

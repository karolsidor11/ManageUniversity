package pl.sidor.ManageUniversity.student.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Imie")
    private String name;

    @Column(name = "Nazwisko")
    private String lastName;

    @Column(name = "Data_urodzenia")
    private Date date;

    @Column(name = "Email")
    private String email;

    @OneToOne
    @JoinColumn(name = "Adres_id")
    private Adres adres;

    @Column(name = "Numer_telefonu")
    private int phoneNumber;

    @Column(name = "Czy_student")
    private boolean isStudent;
}

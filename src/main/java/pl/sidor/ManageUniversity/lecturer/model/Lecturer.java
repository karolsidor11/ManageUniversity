package pl.sidor.ManageUniversity.lecturer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sidor.ManageUniversity.student.model.Adres;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(name = "Imie")
    private String name;

    @Column(name = "Nazwisko")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @OneToOne
    @JoinColumn(name = "Adres_id")
    private Adres adres;

    @Column(name = "Numer_telefonu")
    private int phoneNumber;

    @Column(name = "Stopien")
    private String grade;

}

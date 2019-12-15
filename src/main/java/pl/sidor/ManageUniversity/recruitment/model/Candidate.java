package pl.sidor.ManageUniversity.recruitment.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.recruitment.enums.StudyDirection;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name = "Candidate")
@AllArgsConstructor
@NoArgsConstructor
public class Candidate implements Serializable {

    private static final long serialVersionUID = -1867325787669515921L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Imię kandydata na studia nie  może być puste.")
    private String name;

    @Column(name = "LastName")
    @NotNull(message = "Nazwisko kandydata na sudia nie może być puste.")
    private String lastName;

    @Column(name = "Adres")
    @NotNull(message = "Adres nie może być pusty.")
    private Adres adres;

    @Column(name = "Email")
    @Email(message = "Nieprawidłowy adres email.")
    private String email;

    @Column(name = "PhoneNumber")
    private Integer phoneNumber;

    @Column(name = "StudyDirection")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Kierunek studiów nie może być pusty.")
    private StudyDirection studyDirection;

    @Embedded
    @NotNull(message = "Wyniki egzaminu maturalnego nie mogą być puste.")
    private MaturaResults maturaResults;

    @Column(name = "payForStudy")
    private boolean isPay;
}

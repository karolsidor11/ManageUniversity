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
@AllArgsConstructor
@NoArgsConstructor
public class Candidate implements Serializable {

    private static final long serialVersionUID = -1867325787669515921L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Imię kandydata na studia nie  może być puste.")
    private String name;

    @NotNull(message = "Nazwisko kandydata na sudia nie może być puste.")
    private String lastName;

    @NotNull(message = "Adres nie może być pusty.")
    private Adres adres;

    @Email(message = "Nieprawidłowy adres email.")
    private String email;

    @NotNull(message = "Numer telefonu nie może być pusty.")
    private Integer phoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Kierunek studiów nie może być pusty.")
    private StudyDirection studyDirection;

    @Embedded
    @NotNull(message = "Wyniki egzaminu maturalnego nie mogą być puste.")
    private MaturaResults maturaResults;

    private boolean isPay;
}

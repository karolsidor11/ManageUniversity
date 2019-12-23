package pl.sidor.ManageUniversity.recruitment.model;

import lombok.*;
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
public class PaymentForStudy  implements Serializable{

    private static final long serialVersionUID = 8159359867136567609L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Imię kandydata na studia nie  może być puste.")
    private String name;

    @NotNull(message = "Nazwisko kandydata na sudia nie może być puste.")
    private String lastName;

    @Email(message = "Nieprawidłowy adres email.")
    private String email;

    @Enumerated(EnumType.STRING)
    private StudyDirection studyDirection;

    private double price;
}

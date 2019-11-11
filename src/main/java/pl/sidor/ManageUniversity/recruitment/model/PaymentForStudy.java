package pl.sidor.ManageUniversity.recruitment.model;

import lombok.*;
import pl.sidor.ManageUniversity.recruitment.enums.StudyDirection;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForStudy  implements Serializable{

    private static final long serialVersionUID = 8159359867136567609L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Imię kandydata na studia nie  może być puste.")
    private String name;

    @Column(name = "LastName")
    @NotNull(message = "Nazwisko kandydata na sudia nie może być puste.")
    private String lastName;

    @Column(name = "Email")
    @Email(message = "Nieprawidłowy adres email.")
    private String email;

    @Column(name = "StudyDirection")
    @Enumerated(EnumType.STRING)
    private StudyDirection studyDirection;

    @Column(name = "Price")
    private double price;
}

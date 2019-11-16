package pl.sidor.ManageUniversity.recruitment.model;

import lombok.*;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecrutationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Imię kandydata na studia nie  może być puste.")
    private String name;

    @Column(name = "LastName")
    @NotNull(message = "Nazwisko kandydata na sudia nie może być puste.")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private AcceptedInCollage isAccept;
}

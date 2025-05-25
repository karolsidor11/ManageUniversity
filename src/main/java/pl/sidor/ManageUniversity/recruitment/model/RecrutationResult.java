package pl.sidor.ManageUniversity.recruitment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.sidor.ManageUniversity.recruitment.enums.AcceptedInCollage;

import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecrutationResult  implements Serializable {

    private static final long serialVersionUID = -7845884831837673827L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Imię kandydata na studia nie  może być puste.")
    private String name;

    @NotNull(message = "Nazwisko kandydata na sudia nie może być puste.")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private AcceptedInCollage isAccept;
}

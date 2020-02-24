package pl.sidor.ManageUniversity.lecturer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer implements Serializable {

    private static final long serialVersionUID = 2807717549783551827L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Imię nie może być null.")
    private String name;

    @NotNull(message = "Nazwisko nie może być null.")
    private String lastName;

    @Email(message = "Nieprawidłowy adres eamail.")
    private String email;

    @Embedded
    @NotNull(message = "Adres nie może być null.")
    private Adres adres;

    @NotNull(message = "Numer telefonu nie może być null.")
    private Integer phoneNumber;

    @NotNull(message = "Stopień naukowy nie może byc null.")
    private String grade;

    @OneToOne()
    private Subject subject;
}

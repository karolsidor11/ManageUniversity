package pl.sidor.ManageUniversity.lecturer.model;

import lombok.*;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(name, lecturer.name) && Objects.equals(lastName, lecturer.lastName) && Objects.equals(email, lecturer.email) && Objects.equals(adres, lecturer.adres) && Objects.equals(phoneNumber, lecturer.phoneNumber) && Objects.equals(grade, lecturer.grade) && Objects.equals(subject, lecturer.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, email, adres, phoneNumber, grade, subject);
    }
}

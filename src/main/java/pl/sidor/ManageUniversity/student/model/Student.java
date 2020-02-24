package pl.sidor.ManageUniversity.student.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.model.Adres;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 8573582585029283903L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Imie nie może być null.")
    private String name;

    @NotNull(message = "Nazwisko nie może być null.")
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Email(message = "Nieprawidłowy adres email.")
    private String email;

    @Embedded
    @NotNull(message = "Adres nie może być null.")
    private Adres adres;

    @NotNull(message = "Numer telefonu nie może być null.")
    private Integer phoneNumber;

    @NotNull(message = "Student nie może być null.")
    private boolean isStudent;

    @NotNull(message = "Grupa studencka nie może być null.")
    private Double studentGroup;

    @OneToOne
    @JoinColumn()
    private StudentRatingsCard studentRatingsCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return isStudent == student.isStudent && Objects.equals(name, student.name) && Objects.equals(lastName, student.lastName) && Objects.equals(dateOfBirth, student.dateOfBirth) && Objects.equals(email, student.email) && Objects.equals(adres, student.adres) && Objects.equals(phoneNumber, student.phoneNumber) && Objects.equals(studentGroup, student.studentGroup) && Objects.equals(studentRatingsCard, student.studentRatingsCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, dateOfBirth, email, adres, phoneNumber, isStudent, studentGroup, studentRatingsCard);
    }
}

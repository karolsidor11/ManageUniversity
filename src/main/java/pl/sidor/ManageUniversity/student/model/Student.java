package pl.sidor.ManageUniversity.student.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.model.Adres;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "Name")
    @NotNull(message = "Imie nie może być null.")
    private String name;

    @Column(name = "LastName")
    @NotNull(message = "Nazwisko nie może być null.")
    private String lastName;

    @Column(name = "Date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @Column(name = "Email")
    @Email(message = "Nieprawidłowy adres email.")
    private String email;

    @Embedded
    @NotNull(message = "Adres nie może być null.")
    private Adres adres;

    @Column(name = "PhoneNumber")
    private Integer phoneNumber;

    @Column(name = "isStudent")
    @NotNull(message = "Czy Student nie może być null.")
    private boolean isStudent;

    @Column(name = "StudentGroup")
    private Double studentGroup;

    @OneToOne
    @JoinColumn(name = "ratings_id")
    private StudentRatingsCard studentRatingsCard;
}

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
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecturer  implements Serializable{

    private static final long serialVersionUID = 2807717549783551827L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Imię nie może być null.")
    private String name;

    @Column(name = "LastName")
    @NotNull(message = "Nazwisko nie może być null.")
    private String lastName;

    @Column(name = "Email")
    @Email(message = "Nieprawidłowy adres eamail.")
    private String email;

    @Embedded
    @NotNull(message = "Adres nie może być null.")
    private Adres adres;

    @Column(name = "PhoneNumber")
    @NotNull(message = "Nieprawidłowy numer telefonu. Numer musi być 9 cyfrowy.")
    @Size(min = 9, max = 9)
    private int phoneNumber;

    @Column(name = "Degree")
    @NotNull(message = "Stopień naukowy nie może byc null.")
    private String grade;

    @OneToMany
    @Column(name = "SubjectID")
    private List<Subject> subject;

}

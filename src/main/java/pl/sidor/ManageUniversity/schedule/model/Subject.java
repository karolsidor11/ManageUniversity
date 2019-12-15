package pl.sidor.ManageUniversity.schedule.model;

import lombok.*;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {

    private static final long serialVersionUID = -5195553206423356155L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Nazwa przedmiotu nie może być null.")
    private String name;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "schedule_2_subject",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    @NotNull(message = "Lista wykładowców nie może być null.")
    private List<Lecturer> lecturer;

    @Column(name = "roomNumber")
    @NotNull(message = "Numer sali nie może byc null.")
    private int roomNumber;

    @Column(name = "startTime")
    @Past(message = "Podana godzina musi być z przyszłości.")
    private LocalTime startTime;

    @Column(name = "endTime")
    @Past(message = "Podana godzina musi być z przyszłości.")
    private LocalTime endTime;
}

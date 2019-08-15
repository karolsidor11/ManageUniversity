package pl.sidor.ManageUniversity.schedule.model;

import lombok.*;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
//    @NotNull(message = "Nazwa przedmiotu nie może być null.")
    private String name;

    @OneToMany
    @JoinColumn(name = "Subject_id")
//    @NotNull(message = "Lista wykładowców nie może być null.")
    private List<Lecturer> lecturer;

    @Column(name = "roomNumber")
//    @NotNull(message = "Numer sali nie może byc null.")
    private int roomNumber;

    @Column(name = "startTime")
//    @Past(message = "Podana godzina musi być z przyszłości.")
    private LocalTime startTime;

    @Column(name = "endTime")
//    @Past(message = "Podana godzina musi być z przyszłości.")
    private LocalTime endTime;

    @OneToMany(mappedBy = "subjects")
    private List<Schedule> schedule;

}

package pl.sidor.ManageUniversity.schedule.model;

import lombok.Builder;
import lombok.Data;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalTime;
import java.util.List;


@Entity
@Data
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "subjectName")
    @NotNull(message = "Nazwa przedmiotu nie może być null.")
    private String name;

    @OneToMany
    @JoinColumn(name = "lectuere_id")
    @NotNull(message = "Lista wykładowców nie może być null.")
    private List<Lecturer> lecturer;

    @Column(name = "roomNumber")
    @NotNull(message = "Numer sali nie może byc null.")
    private int roomNumber;

    @Column(name = "StartTime")
    @Past(message = "Podana godzina musi być z przyszłości.")
    private LocalTime startTime;

    @Column(name = "EndTime")
    @Past(message = "Podana godzina musi być z przyszłości.")
    private LocalTime endTime;


}

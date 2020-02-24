package pl.sidor.ManageUniversity.schedule.model;

import lombok.*;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {

    private static final long serialVersionUID = -5195553206423356155L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Nazwa przedmiotu nie może być null.")
    private String name;

    @OneToOne( cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @NotNull(message = "Lista wykładowców nie może być null.")
    private Lecturer lecturer;

    @NotNull(message = "Numer sali nie może byc null.")
    private int roomNumber;

    private LocalTime startTime;

    private LocalTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return roomNumber == subject.roomNumber && Objects.equals(name, subject.name) && Objects.equals(lecturer, subject.lecturer) && Objects.equals(startTime, subject.startTime) && Objects.equals(endTime, subject.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lecturer, roomNumber, startTime, endTime);
    }
}

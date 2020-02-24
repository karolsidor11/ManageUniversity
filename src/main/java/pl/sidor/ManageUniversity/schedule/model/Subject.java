package pl.sidor.ManageUniversity.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

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

    @NotNull(message = "Nazwa przedmiotu nie może być null.")
    private String name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotNull(message = "Lista wykładowców nie może być null.")
    private Lecturer lecturer;

    @NotNull(message = "Numer sali nie może byc null.")
    private int roomNumber;

    private LocalTime startTime;

    private LocalTime endTime;
}

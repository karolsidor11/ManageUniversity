package pl.sidor.ManageUniversity.schedule.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "DayOfWeek")
    @NotNull(message = "Dzień tygodnia nie może być null.")
    private DayOfWeek dayOfWeek;

    @OneToMany
    @NotNull(message = "Lista przedmiotów nie może być null.")
    private List<Subject> subjects;
}

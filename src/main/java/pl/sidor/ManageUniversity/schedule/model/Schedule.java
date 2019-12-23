package pl.sidor.ManageUniversity.schedule.model;

import lombok.*;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule  implements Serializable{

    private static final long serialVersionUID = -5710572541401522890L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Dzień tygodnia nie może być null.")
    private Days dayOfWeek;

    @NotNull(message = "Grupa  studencka nie może być null.")
    private double studentGroup;

    private int weekNumber;

    @OneToMany(cascade= PERSIST)
    @JoinTable(name = "schedule_2_subject",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects ;
}

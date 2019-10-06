package pl.sidor.ManageUniversity.schedule.model;

import lombok.*;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule  implements Serializable{

    private static final long serialVersionUID = -5710572541401522890L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DayOfWeek")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Dzień tygodnia nie może być null.")
    private Days dayOfWeek;

    @Column(name = "StudentGroup")
    private Double studentGroup;

    @Column(name = "WeekNumber")
    @Size(min = 1, max = 52)
    private Integer weekNumber;

    @OneToMany(cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "schedule_2_subject",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects ;
}

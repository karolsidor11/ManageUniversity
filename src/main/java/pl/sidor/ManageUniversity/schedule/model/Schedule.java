package pl.sidor.ManageUniversity.schedule.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DayOfWeek")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Dzień tygodnia nie może być null.")
    private Days dayOfWeek;

    @ManyToMany(cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "schedule_2_subject",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects ;
}

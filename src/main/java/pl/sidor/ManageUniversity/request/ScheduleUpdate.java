package pl.sidor.ManageUniversity.request;

import lombok.*;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdate  implements Serializable{

    private Days dayOfWeek;
    private Integer weekNumber;
    private Subject subjects;
}

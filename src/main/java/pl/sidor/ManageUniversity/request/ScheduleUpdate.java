package pl.sidor.ManageUniversity.request;

import lombok.*;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdate  implements Serializable{

    private Days dayOfWeek;
    private int weekNumber;
    private Subject subjects;
}

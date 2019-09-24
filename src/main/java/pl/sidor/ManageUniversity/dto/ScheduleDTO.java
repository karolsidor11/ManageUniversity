package pl.sidor.ManageUniversity.dto;

import lombok.*;
import pl.sidor.ManageUniversity.schedule.enums.Days;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Days dayOfWeek;
    private Double studentGroup;
    private Integer weekNumber;
    private List<SubjectDTO> subjects;
}

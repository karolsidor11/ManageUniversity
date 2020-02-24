package pl.sidor.ManageUniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sidor.ManageUniversity.schedule.enums.Days;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private Days dayOfWeek;
    private Double studentGroup;
    private Integer weekNumber;
    private List<SubjectDTO> subjects;
}

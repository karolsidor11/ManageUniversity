package pl.sidor.ManageUniversity.dto;

import lombok.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private String name;
    private List<LecturerDTO> lecturerDTO;
    private int roomNumber;
    private LocalTime startTime;
    private LocalTime endTime;
}

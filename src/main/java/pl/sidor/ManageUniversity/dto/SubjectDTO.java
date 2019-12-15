package pl.sidor.ManageUniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {

    private String name;
    private List<LecturerDTO> lecturerDTO;
    private int roomNumber;
    private LocalTime startTime;
    private LocalTime endTime;
}

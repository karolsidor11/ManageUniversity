package pl.sidor.ManageUniversity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDTO {

    private String name;
    private String lastName;
    private String email;
    private String grade;
}

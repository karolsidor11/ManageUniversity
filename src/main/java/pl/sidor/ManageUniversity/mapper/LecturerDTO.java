package pl.sidor.ManageUniversity.mapper;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDTO {

    private String name;
    private String lastName;
    private String email;
    private String grade;
}

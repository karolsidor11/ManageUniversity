package pl.sidor.ManageUniversity.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindScheduleRequest {

    private String name;
    private String lastName;
    private Integer weekNumber;
}

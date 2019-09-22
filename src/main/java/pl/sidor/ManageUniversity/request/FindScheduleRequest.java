package pl.sidor.ManageUniversity.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindScheduleRequest {

    private String name;
    private String lastName;
    private Integer weekNumber;
}

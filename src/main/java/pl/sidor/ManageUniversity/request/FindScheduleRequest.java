package pl.sidor.ManageUniversity.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindScheduleRequest {

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private int weekNumber;
}

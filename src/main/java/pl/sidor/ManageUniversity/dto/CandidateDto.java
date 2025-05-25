package pl.sidor.ManageUniversity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CandidateDto {

    @NotNull
    private final String name;
    @NotNull
    private final String lastName;
    @NotNull
    private final String email;
}

package pl.sidor.ManageUniversity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

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

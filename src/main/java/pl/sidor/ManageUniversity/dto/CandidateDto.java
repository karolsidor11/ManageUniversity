package pl.sidor.ManageUniversity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDto {

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
}

package pl.sidor.ManageUniversity.lecturer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LecturerDto {

    private final String email;
    private final int phoneNumber;
}

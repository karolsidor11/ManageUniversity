package pl.sidor.ManageUniversity.lecturer.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class LecturerDto {

    private String email;
    private int phoneNumber;
}

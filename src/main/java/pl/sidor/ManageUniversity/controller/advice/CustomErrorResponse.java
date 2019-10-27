package pl.sidor.ManageUniversity.controller.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
class CustomErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd hh:mm:ss")
    private LocalDateTime localDateTime;
    private int status;
    private String errorMessage;
}

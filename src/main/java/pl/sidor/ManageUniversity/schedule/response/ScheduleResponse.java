package pl.sidor.ManageUniversity.schedule.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {

    private Header header;
    private Schedule schedule;
    private Error error;

    public static ScheduleResponse prepareResponse(Optional<Schedule> schedule, Error error) {
        return schedule.isPresent() ? buildSuccessResponse(schedule.get()) : buildErrorResponse(error);
    }

    private static ScheduleResponse buildSuccessResponse(Schedule schedule) {
        return ScheduleResponse.builder().header(Header.getInstance()).schedule(schedule).build();
    }

    private static ScheduleResponse buildErrorResponse(Error error) {
        return ScheduleResponse.builder().header(Header.getInstance()).error(error).build();
    }
}

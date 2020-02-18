package pl.sidor.ManageUniversity.schedule.response;

import lombok.*;
import pl.sidor.ManageUniversity.exception.Error;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {

    private Header header;
    private Schedule schedule;
    private Error error;

    public static ScheduleResponse prepareScheduleResponse(Schedule schedule){
        ScheduleResponse scheduleResponse= new ScheduleResponse();
        scheduleResponse.setHeader(Header.getInstance());
        scheduleResponse.setSchedule(schedule);
        return scheduleResponse;
    }

    public static ScheduleResponse prepareScheduleResponse(Error error){
        ScheduleResponse scheduleResponse= new ScheduleResponse();
        scheduleResponse.setHeader(Header.getInstance());
        scheduleResponse.setError(error);
        return scheduleResponse;
    }
}

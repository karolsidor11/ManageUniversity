package pl.sidor.ManageUniversity.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;

@Slf4j
@RequiredArgsConstructor
public class StudentScheduler {

    private final StudentRepo studentRepo;

    @Scheduled(fixedDelayString = "50000")
    void execute() {
        log.info("Student scheduler start.");
        try {
            final Integer studentDeletedCount = studentRepo.deleteAllNotStudents();
            log.info("Deleted students: {}", studentDeletedCount);
        } catch (Exception exception) {
            log.error("Student scheduler  call error: {}", exception.getMessage());
        }

        log.info("Student scheduler stop.");
    }
}

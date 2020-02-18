package pl.sidor.ManageUniversity.controller;

        import lombok.AllArgsConstructor;
        import org.springframework.core.io.InputStreamResource;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;
        import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
        import pl.sidor.ManageUniversity.pdf.LecturerPdfGenerator;
        import pl.sidor.ManageUniversity.pdf.SchedulePdfGenerator;
        import pl.sidor.ManageUniversity.pdf.StudentPdfGenerator;
        import pl.sidor.ManageUniversity.pdf.SubjectPdfGenerator;
        import pl.sidor.ManageUniversity.schedule.response.ScheduleResponse;
        import pl.sidor.ManageUniversity.schedule.response.SubjectResponse;
        import pl.sidor.ManageUniversity.schedule.service.ScheduleService;
        import pl.sidor.ManageUniversity.schedule.service.SubjectService;
        import pl.sidor.ManageUniversity.student.response.StudentResponse;
        import pl.sidor.ManageUniversity.student.service.StudentService;

        import java.io.ByteArrayInputStream;

@RestController
@AllArgsConstructor
@RequestMapping(value = "reports")
public class ReportsController {

    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final SubjectService subjectService;
    private final ScheduleService scheduleService;
    private final StudentPdfGenerator studentPdfGenerator;
    private final LecturerPdfGenerator lecturerPdfGenerator;
    private final SubjectPdfGenerator subjectPdfGenerator;
    private final SchedulePdfGenerator schedulePdfGenerator;

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getStudent(@PathVariable Long id) {
        StudentResponse byId = studentService.findById(id);
        ByteArrayInputStream stream = studentPdfGenerator.studentReport(byId.getStudent());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(stream));
    }

    @GetMapping(value = "/lecturer/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> lecturerReport(@PathVariable Long id) {
        LecturerResponse lecturerResponse = lecturerService.findById(id);
        ByteArrayInputStream stream = lecturerPdfGenerator.lecturerReport(lecturerResponse.getLecturer());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(stream));
    }

    @GetMapping(value = "/subject/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> subjectReport(@PathVariable Long id) {
        SubjectResponse subjectServiceById = subjectService.getById(id);
        ByteArrayInputStream stream = subjectPdfGenerator.subjectReport(subjectServiceById.getSubject());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(stream));
    }

    @GetMapping(value = "/schedule/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> scheduleReport(@PathVariable Long id){
        ScheduleResponse scheduleById = scheduleService.getScheduleById(id);
        ByteArrayInputStream stream = schedulePdfGenerator.studentReport(scheduleById.getSchedule());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(stream));
    }
}


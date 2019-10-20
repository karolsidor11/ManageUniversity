package pl.sidor.ManageUniversity.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo;
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo;
import pl.sidor.ManageUniversity.evaluation.service.RatingSetService;
import pl.sidor.ManageUniversity.evaluation.service.RatingSetServiceImpl;
import pl.sidor.ManageUniversity.evaluation.service.StudentRatingsCardService;
import pl.sidor.ManageUniversity.evaluation.service.StudentRatingsCardServiceImpl;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
import pl.sidor.ManageUniversity.lecturer.service.LecturerServiceImpl;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.service.ScheduleService;
import pl.sidor.ManageUniversity.schedule.service.ScheduleServiceImpl;
import pl.sidor.ManageUniversity.schedule.service.SubjectService;
import pl.sidor.ManageUniversity.schedule.service.SubjectServiceImpl;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.service.StudentService;
import pl.sidor.ManageUniversity.student.service.StudentServiceImpl;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return  new ObjectMapper();
    }

    @Bean
    public CheckUniqeStudentPredicate checkUniqeStudentPredicate(StudentRepo studentRepo){
        return new CheckUniqeStudentPredicate(studentRepo);
    }

    @Bean
    public CheckLecturer checkLecturer(LecturerRepo lecturerRepo){
        return  new CheckLecturer(lecturerRepo);
    }

    @Bean
    public StudentService studentService(StudentRepo studentRepo, CheckUniqeStudentPredicate checkUniqeStudentPredicate,
                                         ScheduleRepo scheduleRepo){
        return  new StudentServiceImpl(studentRepo,checkUniqeStudentPredicate, scheduleRepo );
    }

    @Bean
    public LecturerService lecturerService(LecturerRepo lecturerRepo, CheckLecturer checkLecturer
                                           , SubjectRepo subjectRepo,ScheduleRepo scheduleRepo ){
        return new LecturerServiceImpl(lecturerRepo, checkLecturer,subjectRepo,scheduleRepo);
    }

    @Bean
    public RatingSetService ratingSetService(RatingRepo ratingRepo){
        return  new RatingSetServiceImpl(ratingRepo);
    }

    @Bean
    public StudentRatingsCardService studentRatingsCard(StudentCardRepo studentCardRepo, StudentRepo studentRepo){
        return  new StudentRatingsCardServiceImpl(studentCardRepo,studentRepo);
    }

    @Bean
    public SubjectValidator subjectValidator(SubjectRepo subjectRepo){
        return  new SubjectValidator(subjectRepo);
    }

    @Bean
    public SubjectService subjectService(SubjectRepo subjectRepo, SubjectValidator subjectValidator, LecturerRepo lecturerRepo){
        return  new SubjectServiceImpl(subjectRepo,subjectValidator,lecturerRepo);
    }

    @Bean
    public ScheduleValidator scheduleValidator(ScheduleRepo scheduleRepo){
        return  new ScheduleValidator(scheduleRepo);
    }

    @Bean
    public ScheduleService scheduleService(ScheduleRepo scheduleRepo, ScheduleValidator scheduleValidator){
        return  new ScheduleServiceImpl(scheduleRepo,scheduleValidator);
    }
}

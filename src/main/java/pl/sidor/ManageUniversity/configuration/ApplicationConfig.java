package pl.sidor.ManageUniversity.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import pl.sidor.ManageUniversity.evaluation.repository.RatingRepo;
import pl.sidor.ManageUniversity.evaluation.repository.StudentCardRepo;
import pl.sidor.ManageUniversity.evaluation.ratingset.RatingSetService;
import pl.sidor.ManageUniversity.evaluation.ratingset.RatingSetServiceImpl;
import pl.sidor.ManageUniversity.evaluation.ratingcard.StudentRatingsCardService;
import pl.sidor.ManageUniversity.evaluation.ratingcard.StudentRatingsCardServiceImpl;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
import pl.sidor.ManageUniversity.lecturer.service.LecturerServiceImpl;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;
import pl.sidor.ManageUniversity.recruitment.repository.CandidateRepo;
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo;
import pl.sidor.ManageUniversity.recruitment.repository.RecrutationResultRepo;
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateService;
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateServiceImpl;
import pl.sidor.ManageUniversity.recruitment.service.payments.PaymentService;
import pl.sidor.ManageUniversity.recruitment.service.payments.PaymentServiceImpl;
import pl.sidor.ManageUniversity.recruitment.service.recrutation.RecrutationService;
import pl.sidor.ManageUniversity.recruitment.service.recrutationResult.RecrutationResultService;
import pl.sidor.ManageUniversity.recruitment.service.recrutationResult.RecrutationResultServiceImpl;
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

import javax.persistence.EntityManager;

@Configuration
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @Bean
    public CheckUniqeStudentPredicate checkUniqeStudentPredicate(StudentRepo studentRepo) {
        return new CheckUniqeStudentPredicate(studentRepo);
    }

    @Bean
    public CheckLecturer checkLecturer(LecturerRepo lecturerRepo) {
        return new CheckLecturer(lecturerRepo);
    }

    @Bean
    public StudentService studentService(StudentRepo studentRepo, CheckUniqeStudentPredicate checkUniqeStudentPredicate, ScheduleRepo scheduleRepo) {
        return new StudentServiceImpl(studentRepo, checkUniqeStudentPredicate, scheduleRepo);
    }

    @Bean
    public LecturerService lecturerService(LecturerRepo lecturerRepo, SubjectRepo subjectRepo, ScheduleRepo scheduleRepo) {
        return new LecturerServiceImpl(lecturerRepo, subjectRepo, scheduleRepo);
    }

    @Bean
    public RatingSetService ratingSetService(RatingRepo ratingRepo) {
        return new RatingSetServiceImpl(ratingRepo);
    }

    @Bean
    public StudentRatingsCardService studentRatingsCard(StudentCardRepo studentCardRepo, StudentRepo studentRepo) {
        return new StudentRatingsCardServiceImpl(studentCardRepo, studentRepo);
    }

    @Bean
    public SubjectValidator subjectValidator(SubjectRepo subjectRepo) {
        return new SubjectValidator(subjectRepo);
    }

    @Bean
    public SubjectService subjectService(SubjectRepo subjectRepo, SubjectValidator subjectValidator, LecturerRepo lecturerRepo) {
        return new SubjectServiceImpl(subjectRepo, subjectValidator, lecturerRepo);
    }

    @Bean
    public ScheduleValidator scheduleValidator(ScheduleRepo scheduleRepo) {
        return new ScheduleValidator(scheduleRepo);
    }

    @Bean
    public ScheduleService scheduleService(ScheduleRepo scheduleRepo, ScheduleValidator scheduleValidator) {
        return new ScheduleServiceImpl(scheduleRepo, scheduleValidator);
    }

    @Bean
    public CandidateService candidateService(CandidateRepo candidateRepo, RecrutationService recrutationService, RecrutationResultRepo recrutationResultRepo) {
        return new CandidateServiceImpl(candidateRepo, recrutationService, recrutationResultRepo);
    }

    @Bean
    public PaymentService paymentService(PaymentRepo paymentRepo, EntityManager entityManager) {
        return new PaymentServiceImpl(entityManager, paymentRepo);
    }

    @Bean
    public RecrutationResultService recrutationResultService(RecrutationResultRepo recrutationResultRepo) {
        return new RecrutationResultServiceImpl(recrutationResultRepo);
    }
}

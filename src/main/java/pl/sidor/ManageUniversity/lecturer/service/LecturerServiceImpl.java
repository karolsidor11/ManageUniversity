package pl.sidor.ManageUniversity.lecturer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@Service
@Slf4j
public class LecturerServiceImpl implements LecturerService {

    private LecturerRepo lecturerRepo;
    private CheckLecturer checkLecturer;

    @Autowired
    public LecturerServiceImpl(LecturerRepo lecturerRepo,CheckLecturer checkLecturer) {
        this.lecturerRepo = lecturerRepo;
        this.checkLecturer = checkLecturer;
    }

    @Override
    public Lecturer findById(long id) throws UniversityException {

        Optional<Lecturer> byId = lecturerRepo.findById(id);
        if (!byId.isPresent()) {
            log.info("Podano nieprawidłowy identyfikator");
            throw ExceptionFactory.incorrectLecturerID(String.valueOf(id));
        }
        return byId.get();
    }

    @Override
    public Lecturer create(Lecturer lecturer) throws Throwable {

        of(lecturer).filter(checkLecturer)
                .map(lecturer1 -> lecturerRepo.save(lecturer))
                .orElseThrow(ExceptionFactory.lecturerInDatabase(lecturer.getEmail()));
        return lecturerRepo.save(lecturer);
    }

    @Override
    public void update(Lecturer lecturer) throws UniversityException {

        Optional<Lecturer> byId = lecturerRepo.findById(lecturer.getId());
        if(!byId.isPresent()){
            throw   ExceptionFactory.incorrectLecturerID(String .valueOf(lecturer.getId()));
        }
        Lecturer lecturer1 = byId.get();
        lecturer1.builder()
                .id(lecturer.getId())
                .name(lecturer.getName())
                .lastName(lecturer.getLastName())
                .email(lecturer.getEmail())
                .adres(lecturer.getAdres())
                .phoneNumber(lecturer.getPhoneNumber())
                .grade(lecturer.getGrade())
                .build();

        lecturerRepo.save(lecturer1);
    }

    @Override
    public void delete(long id) throws UniversityException {
        Optional<Lecturer> byId = lecturerRepo.findById(id);
        byId.ifPresent(lecturer -> lecturerRepo.deleteById(id));
        if (!byId.isPresent()) {
            throw ExceptionFactory.incorrectLecturerID(String.valueOf(id));
        }
    }
}

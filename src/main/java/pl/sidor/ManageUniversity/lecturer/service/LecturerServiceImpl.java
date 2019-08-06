package pl.sidor.ManageUniversity.lecturer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;

import java.util.Optional;

@Service
public class LecturerServiceImpl implements LecturerService {

    private LecturerRepo lecturerRepo;

    @Autowired
    public LecturerServiceImpl(LecturerRepo lecturerRepo) {
        this.lecturerRepo = lecturerRepo;
    }

    @Override
    public Lecturer findById(long id) {
        return lecturerRepo.findById(id).get();
    }

    @Override
    public Lecturer create(Lecturer lecturer) {
        return lecturerRepo.save(lecturer);
    }

    @Override
    public void update(Lecturer lecturer) {

        Optional<Lecturer> byId = lecturerRepo.findById(lecturer.getId());
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
    public void delete(long id) {

        lecturerRepo.deleteById(id);
    }
}

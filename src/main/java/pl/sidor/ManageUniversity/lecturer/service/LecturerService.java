package pl.sidor.ManageUniversity.lecturer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.lecturer.dao.LecturerDao;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;

import java.util.Optional;

@Service
public class LecturerService implements LecturerDao {

    private LecturerRepo lecturerRepo;

    @Autowired
    public LecturerService(LecturerRepo lecturerRepo) {
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

        lecturer1.setId(lecturer.getId());
        lecturer1.setName(lecturer.getName());
        lecturer1.setLastName(lecturer.getLastName());
        lecturer1.setEmail(lecturer.getEmail());
        lecturer1.setAdres(lecturer.getAdres());
        lecturer1.setGrade(lecturer.getGrade());
        lecturer1.setPhoneNumber(lecturer.getPhoneNumber());

        lecturerRepo.save(lecturer1);

    }

    @Override
    public void delete(long id) {

        lecturerRepo.deleteById(id);
    }
}

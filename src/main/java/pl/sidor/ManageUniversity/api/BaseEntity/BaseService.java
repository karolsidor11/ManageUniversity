package pl.sidor.ManageUniversity.api.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    private BaseRepo baseRepo;

    @Autowired
    public BaseService(BaseRepo baseRepo) {
        this.baseRepo = baseRepo;
    }

    public BaseEntity findByName(String name) {
        return baseRepo.findByName(name);
    }

    public BaseEntity findByID(Integer id) {
        return baseRepo.findById(id).get();
    }
}

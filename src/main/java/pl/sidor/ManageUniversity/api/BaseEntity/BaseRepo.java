package pl.sidor.ManageUniversity.api.BaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepo extends JpaRepository<BaseEntity, Integer> {

    BaseEntity findByName(String name);
}

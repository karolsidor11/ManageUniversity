package pl.sidor.ManageUniversity.evaluation.service;

import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;

@Component
public interface StudentRatingsCardService {

    StudentRatingsCard findByID(Long id) throws Throwable;

    StudentRatingsCard findByStudent(String name, String lastName);

    StudentRatingsCard createCard(StudentRatingsCard studentRatingsCard);

    StudentRatingsCard updateCard(StudentRatingsCard studentRatingsCard);

    boolean deleteCard(Long id);

}

package pl.sidor.ManageUniversity.evaluation.ratingcard;

import pl.sidor.ManageUniversity.evaluation.model.StudentRatingsCard;

public interface StudentRatingsCardService {

    StudentRatingsCard findByID(final Long id) throws Throwable;

    StudentRatingsCard findByStudent(final String name, final String lastName) throws Throwable;

    StudentRatingsCard createCard(final StudentRatingsCard studentRatingsCard) throws Throwable;

    StudentRatingsCard updateCard(final StudentRatingsCard studentRatingsCard) throws Throwable;

    boolean deleteCard(final Long id);

}

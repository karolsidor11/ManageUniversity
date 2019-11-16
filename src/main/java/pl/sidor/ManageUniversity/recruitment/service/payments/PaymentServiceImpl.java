package pl.sidor.ManageUniversity.recruitment.service.payments;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.Candidate_;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @PersistenceContext
    private final EntityManager entityManager;
    private PaymentRepo paymentRepo;

    @Override
    public PaymentForStudy pay(PaymentForStudy paymentForStudy) throws UniversityException {
        if (Objects.isNull(paymentForStudy)) {
            throw ExceptionFactory.objectIsEmpty("PaymentForStudy.");
        }

        Candidate candidateToPayment = findCandidateToPayment(paymentForStudy);
        candidateToPayment.setPay(true);

        entityManager.merge(candidateToPayment);

        return paymentRepo.save(paymentForStudy);
    }

    @Override
    public PaymentForStudy checkPayments(String name, String lastName) throws Throwable {

        Optional<PaymentForStudy> byNameAndLastName = paymentRepo.findByNameAndLastName(name, lastName);

        return byNameAndLastName.orElseThrow(ExceptionFactory.objectIsEmpty("Brak płatności"));
    }

    private Candidate findCandidateToPayment(PaymentForStudy candidate) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candidate> query = criteriaBuilder.createQuery(Candidate.class);
        Root<Candidate> root = query.from(Candidate.class);

        Predicate namePredicate = criteriaBuilder.equal(root.get(Candidate_.name), candidate.getName());
        Predicate lastNamePredicate = criteriaBuilder.equal(root.get(Candidate_.lastName), candidate.getLastName());

        query.where(namePredicate, lastNamePredicate);

        return entityManager.createQuery(query).getResultList().get(0);
    }
}

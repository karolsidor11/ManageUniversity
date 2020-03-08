package pl.sidor.ManageUniversity.recruitment.service.payments;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.Candidate_;
import pl.sidor.ManageUniversity.recruitment.model.PaymentForStudy;
import pl.sidor.ManageUniversity.recruitment.repository.PaymentRepo;
import pl.sidor.ManageUniversity.recruitment.response.PaymentResponse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.Optional;

@Transactional
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @PersistenceContext
    private final EntityManager entityManager;
    private PaymentRepo paymentRepo;

    @Override
    public PaymentResponse pay(final PaymentForStudy paymentForStudy) {
        if (Objects.isNull(paymentForStudy)) {
            return PaymentResponse.prepareResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        Optional.ofNullable(findCandidateToPayment(paymentForStudy)).ifPresent((candidate) -> {
            candidate.setPay(true);
            entityManager.merge(candidate);
        });
        return PaymentResponse.prepareResponse(Optional.of(paymentRepo.save(paymentForStudy)), ResponseException.pustyObiekt());
    }

    @Override
    public PaymentResponse checkPayments(final String name, final String lastName) {
        Optional<PaymentForStudy> payment = paymentRepo.findByNameAndLastName(name, lastName);
        return PaymentResponse.prepareResponse(payment, ResponseException.pustyObiekt());
    }

    private Candidate findCandidateToPayment(final PaymentForStudy candidate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candidate> query = criteriaBuilder.createQuery(Candidate.class);
        Root<Candidate> root = query.from(Candidate.class);

        Predicate namePredicate = criteriaBuilder.equal(root.get(Candidate_.name), candidate.getName());
        Predicate lastNamePredicate = criteriaBuilder.equal(root.get(Candidate_.lastName), candidate.getLastName());

        query.where(namePredicate, lastNamePredicate);

        return entityManager.createQuery(query).getResultList().stream().findFirst().orElse(null);
    }
}

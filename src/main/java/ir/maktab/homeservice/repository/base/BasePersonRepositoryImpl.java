package ir.maktab.homeservice.repository.base;

import ir.maktab.homeservice.domains.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Optional;

public abstract class BasePersonRepositoryImpl <T extends Person>
        extends SimpleJpaRepository<T>
        implements BasePersonRepository<T> {


    public BasePersonRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<T> findByUserName(String userName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);
        query.where(cb.equal(from.get("userName"), userName));
        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }
}

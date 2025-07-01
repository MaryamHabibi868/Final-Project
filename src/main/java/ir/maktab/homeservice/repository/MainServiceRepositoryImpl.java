package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.repository.base.SimpleJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MainServiceRepositoryImpl
        extends SimpleJpaRepository<MainService>
        implements MainServiceRepository{

    public MainServiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<MainService> getDomainClass() {
        return MainService.class;
    }

    @Override
    public Optional<MainService> findByMainServiceTitle(String mainServiceTitle){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MainService> query = cb.createQuery(getDomainClass());
        Root<MainService> from = query.from(getDomainClass());
        query.select(from);
        query.where(cb.equal(from.get("mainServiceTitle"), mainServiceTitle));
        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }
}

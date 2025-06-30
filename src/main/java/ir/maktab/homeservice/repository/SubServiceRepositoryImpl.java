package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.repository.base.SimpleJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class SubServiceRepositoryImpl
        extends SimpleJpaRepository<SubService>
        implements SubServiceRepository{

    public SubServiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<SubService> getDomainClass() {
        return SubService.class;
    }
}

package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.repository.base.SimpleJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

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
}

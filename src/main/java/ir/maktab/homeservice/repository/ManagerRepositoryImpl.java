package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.repository.base.BasePersonRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepositoryImpl
        extends BasePersonRepositoryImpl<Manager>
        implements ManagerRepository {

    public ManagerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Manager> getDomainClass() {
        return Manager.class;
    }
}

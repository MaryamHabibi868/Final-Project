package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.repository.base.BasePersonRepositoryImpl;
import jakarta.persistence.EntityManager;

public class SpecialistRepositoryImpl
        extends BasePersonRepositoryImpl<Specialist>
        implements SpecialistRepository{

    public SpecialistRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Specialist> getDomainClass() {
        return Specialist.class;
    }
}

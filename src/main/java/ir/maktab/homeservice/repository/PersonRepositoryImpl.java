package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Person;
import ir.maktab.homeservice.repository.base.BasePersonRepositoryImpl;
import jakarta.persistence.EntityManager;

public class PersonRepositoryImpl
        extends BasePersonRepositoryImpl<Person>
        implements PersonRepository{

    public PersonRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Person> getDomainClass() {
        return Person.class;
    }
}

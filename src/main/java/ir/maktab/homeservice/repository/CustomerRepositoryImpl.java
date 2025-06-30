package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.repository.base.BasePersonRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl
        extends BasePersonRepositoryImpl<Customer>
        implements CustomerRepository{

    public CustomerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Customer> getDomainClass() {
        return Customer.class;
    }
}

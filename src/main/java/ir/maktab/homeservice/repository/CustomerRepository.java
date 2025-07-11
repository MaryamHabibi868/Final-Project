package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends BaseUserRepository<Customer> {


}

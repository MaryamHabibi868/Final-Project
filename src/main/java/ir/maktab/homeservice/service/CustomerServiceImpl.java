package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;

public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, CustomerRepository>
        implements CustomerService{

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }
}

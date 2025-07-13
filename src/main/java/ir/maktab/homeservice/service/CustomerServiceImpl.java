package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.mapper.FeedbackMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    private final CustomerMapper customerMapper;



    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper customerMapper) {
        super(repository);
        this.customerMapper = customerMapper;

    }

    //✅
    @Transactional
    @Override
    public CustomerResponse registerCustomer(CustomerSaveRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email address already exist");
        }
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setWallet(Wallet.builder().balance(BigDecimal.ZERO).build());
        Customer save = repository.save(customer);
        return customerMapper.entityMapToResponse(save);
    }

    //✅
    @Transactional
    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request) {
        Customer foundCustomer = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Customer not found")
                );
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("Email address already exist");
        }
        if (request.getFirstName() != null) {
            foundCustomer.setFirstName(request.getFirstName());
        } else if (request.getLastName() != null) {
            foundCustomer.setLastName(request.getLastName());
        } else if (request.getEmail() != null) {
            foundCustomer.setEmail(request.getEmail());
        } else if (request.getPassword() != null) {
            foundCustomer.setPassword(request.getPassword());
        }
        Customer save = repository.save(foundCustomer);
        return customerMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public CustomerResponse loginCustomer(CustomerLoginRequest request) {
        return customerMapper.entityMapToResponse(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Customer Not Found")));
    }

    //✅
    @Override
    public List<CustomerResponse> findAllCustomers() {
        return repository.findUsersByIdNotNull(Customer.class).stream()
                .map(customerMapper::entityMapToResponse)
                .toList();
    }

    //✅
    @Override
    public List<CustomerResponse> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
    (String firstName) {
        return repository.
                findAllByFirstNameContainsIgnoreCaseOrderByIdAsc(firstName)
                .stream()
                .map(customerMapper::entityMapToResponse)
                .toList();
    }

    //✅
    @Override
    public List<CustomerResponse> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
    (String lastName) {
        return repository.
                findAllByLastNameContainsIgnoreCaseOrderByIdAsc(lastName)
                .stream()
                .map(customerMapper::entityMapToResponse)
                .toList();
    }
}
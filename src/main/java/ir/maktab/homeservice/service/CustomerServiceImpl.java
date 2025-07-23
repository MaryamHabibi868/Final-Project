package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Role;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    private final CustomerMapper customerMapper;
    private final RoleService roleService;


    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper customerMapper,
                               RoleService roleService) {
        super(repository);
        this.customerMapper = customerMapper;

        this.roleService = roleService;
    }


    @Transactional
    @Override
    public CustomerResponse registerCustomer(CustomerSaveRequest request) {

        Optional<Customer> existingCustomer = repository.findByEmail(request.getEmail());

        if (existingCustomer.isPresent()) {
            Customer registeredCustomer = existingCustomer.get();
            if (registeredCustomer.getIsEmailVerify()) {
                throw new DuplicatedException("Email address already exist");
            }
        }

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setWallet(wallet);
        wallet.setUserInformation(customer);

        Role role = roleService.findByName("CUSTOMER");
        customer.setRole(role);

        Customer save = repository.save(customer);

        sendVerificationEmail(save);

        return customerMapper.entityMapToResponse(save);
    }


    public void sendVerificationEmail(Customer customer) {
        System.out.println("Please click on your email for verification " +
                repository.findByEmail(customer.getEmail()));
    }


    public CustomerResponse verifyCustomer(Customer customer) {
        Optional<Customer> customer1 = repository.findByEmail(customer.getEmail());
        if (customer1.isEmpty()) {
            throw new NotFoundException("Specialist not found");
        }
        if (customer1.get().getIsEmailVerify()) {
            throw new NotApprovedException("This specialist is already verified ");
        }

        Customer customer2 = customer1.get();
        customer2.setIsEmailVerify(true);
        customer2.setIsActive(true);

        Customer save = repository.save(customer2);

        return customerMapper.entityMapToResponse(save);
    }

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
        }
        if (request.getLastName() != null) {
            foundCustomer.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            foundCustomer.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            foundCustomer.setPassword(request.getPassword());
        }
        Customer save = repository.save(foundCustomer);
        return customerMapper.entityMapToResponse(save);
    }


    @Override
    public CustomerResponse loginCustomer(CustomerLoginRequest request) {
        return customerMapper.entityMapToResponse(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Customer Not Found")));
    }

}
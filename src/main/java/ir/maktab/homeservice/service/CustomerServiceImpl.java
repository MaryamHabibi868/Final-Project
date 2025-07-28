package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.VerificationToken;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.domains.enumClasses.Role;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;


    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper customerMapper,
                               PasswordEncoder passwordEncoder,
                               SecurityUtil securityUtil,
                               VerificationTokenService verificationTokenService,
                               EmailService emailService) {
        super(repository);
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
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
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setWallet(wallet);
        wallet.setUserInformation(customer);

        customer.setRole(Role.ROLE_CUSTOMER);

        Customer save = repository.save(customer);

        sendVerificationEmail(save);

        return customerMapper.entityMapToResponse(save);
    }


    @Override
    public void sendVerificationEmail(Customer customer) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(customer);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        verificationTokenService.save(verificationToken);

        emailService.sendVerificationEmail(customer.getEmail(), token);
    }


    @Override
    public void verifyCustomerEmail(String token) {

        VerificationToken verificationToken =
                verificationTokenService.findByToken(token)
                .orElseThrow(
                        () -> new NotFoundException("Invalid verification token"));

        if (verificationToken.isUsed()) {
            throw new IllegalStateException("This link has already been used.");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("This link is expired.");
        }

        Customer customer = (Customer) verificationToken.getUser();
        customer.setIsEmailVerify(true);
        customer.setIsActive(true);
        repository.save(customer);

        verificationToken.setUsed(true);
        verificationTokenService.save(verificationToken);

        /*Optional<Customer> customer1 = repository.findByEmail(customer.getEmail());
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

        return customerMapper.entityMapToResponse(save);*/
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request) {

        String email = securityUtil.getCurrentUsername();
        Customer foundCustomer = repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Customer not found")
        );


       /* Customer foundCustomer = repository.findById(request.getId())
                .orElseThrow(
                        () -> new NotFoundException("Customer not found")
                );*/

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
            foundCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
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

    @Override
    public Customer findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Customer Not Found"));
    }

}
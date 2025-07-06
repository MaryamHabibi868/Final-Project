package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.CustomerFound;
import ir.maktab.homeservice.dto.CustomerSaveUpdateRequest;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void customDeleteCustomerById(Long id) {
        Optional<Customer> customerFound = customerRepository.findById(id);
        if (customerFound.isPresent()) {
            Customer customer = customerFound.get();
            customer.setIsActive(false);
            customerRepository.save(customer);
        }
        throw new NotFoundException("Customer Not Found");
    }

    public Customer registerCustomer(CustomerSaveUpdateRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        return repository.save(customer);
    }

    public Customer updateCustomer(CustomerSaveUpdateRequest request) {
        Optional<Customer> foundCustomer = repository.findById(request.getId());
        if (foundCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        Customer customer = new Customer();
        customer.setFirstName(foundCustomer.get().getFirstName());
        customer.setLastName(foundCustomer.get().getLastName());
        customer.setEmail(foundCustomer.get().getEmail());
        customer.setPassword(foundCustomer.get().getPassword());
        return repository.save(customer);
    }

    public void deleteCustomer(CustomerFound request) {
       customDeleteCustomerById(request.getId());
    }

    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

    public Customer customerRegistration(CustomerSaveUpdateRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        return repository.save(customer);
    }

    public Customer customerLogin(CustomerSaveUpdateRequest request) {
        Customer customer = new Customer();
        Optional<Customer> foundCustomer = repository.findByEmail(request.getEmail());
        if (foundCustomer.isPresent()) {
            if (foundCustomer.get().getPassword().equals(request.getPassword())) {
                System.out.println("Login Success");
                return foundCustomer.get();
            } else {
                System.out.println("Wrong Password");
            }
        }
        throw new NotFoundException("Customer not found");
    }
}

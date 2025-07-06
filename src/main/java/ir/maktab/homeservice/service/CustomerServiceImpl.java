package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.CustomerFound;
import ir.maktab.homeservice.dto.CustomerSaveUpdateRequest;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
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
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
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

    public CustomerSaveUpdateRequest registerCustomer(CustomerSaveUpdateRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        Customer save = customerRepository.save(customer);
        return customerMapper.customerMapToDTO(save);
    }

    public CustomerSaveUpdateRequest updateCustomer(CustomerSaveUpdateRequest request) {
        if (customerRepository.findById(request.getId()).isPresent()) {
            Customer customer = new Customer();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setEmail(request.getEmail());
            customer.setPassword(request.getPassword());
            Customer save = customerRepository.save(customer);
            return customerMapper.customerMapToDTO(save);
        }
        throw new NotFoundException("Customer Not Found");
    }


    public CustomerSaveUpdateRequest loginCustomer(CustomerSaveUpdateRequest request) {
        return customerMapper.customerMapToDTO(customerRepository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Customer Not Found")));
    }

    public void deleteCustomer(CustomerFound request) {
        customDeleteCustomerById(request.getId());
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }




}
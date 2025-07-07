package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.CustomerFound;
import ir.maktab.homeservice.dto.CustomerSaveUpdateRequest;
import ir.maktab.homeservice.dto.FeedbackSubmit;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.mapper.FeedBackMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    /*private final CustomerRepository customerRepository;*/
    private final CustomerMapper customerMapper;
    private final OfferOfSpecialistService offerOfSpecialistService;
    private final FeedbackService feedbackService;
    private final FeedBackMapper feedBackMapper;

    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper customerMapper,
                               OfferOfSpecialistService offerOfSpecialistService,
                               FeedbackService feedbackService,
                               FeedBackMapper feedBackMapper) {
        super(repository);
        this.customerMapper = customerMapper;
        this.offerOfSpecialistService = offerOfSpecialistService;
        this.feedbackService = feedbackService;
        this.feedBackMapper = feedBackMapper;
    }

   /* public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }*/

    @Override
    public void customDeleteCustomerById(Long id) {
        Optional<Customer> customerFound = repository.findById(id);
        if (customerFound.isPresent()) {
            Customer customer = customerFound.get();
            customer.setIsActive(false);
            repository.save(customer);
        }
        throw new NotFoundException("Customer Not Found");
    }

    @Override
    public CustomerSaveUpdateRequest registerCustomer(CustomerSaveUpdateRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        Customer save = repository.save(customer);
        return customerMapper.customerMapToDTO(save);
    }

    @Override
    public CustomerSaveUpdateRequest updateCustomer(CustomerSaveUpdateRequest request) {
        if (repository.findById(request.getId()).isPresent()) {
            Customer customer = new Customer();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setEmail(request.getEmail());
            customer.setPassword(request.getPassword());
            Customer save = repository.save(customer);
            return customerMapper.customerMapToDTO(save);
        }
        throw new NotFoundException("Customer Not Found");
    }


    @Override
    public CustomerSaveUpdateRequest loginCustomer(CustomerSaveUpdateRequest request) {
        return customerMapper.customerMapToDTO(repository.
                findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Customer Not Found")));
    }

    public void deleteCustomer(CustomerFound request) {
        customDeleteCustomerById(request.getId());
    }

    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

    @Override
    public FeedbackSubmit submitFeedback(FeedbackSubmit feedbackSubmit, Long offerOfSpecialistId) {
        FeedBack feedBack1 = feedBackMapper.feedbackDTOMapToEntity(feedbackSubmit);
        if (!feedBack1.getOfferOfSpecialist().getId().equals(offerOfSpecialistId)) {
            throw new NotFoundException("Offer Of Specialist Not Found");
        }
        FeedBack feedBack = new FeedBack();
        feedBack.setFeedbackRange(feedbackSubmit.getFeedbackRange());
        feedBack.setFeedbackType(feedbackSubmit.getFeedbackType());
        FeedBack save = feedbackService.save(feedBack);
        return feedBackMapper.feedbackMapToDTO(save);
    }
}
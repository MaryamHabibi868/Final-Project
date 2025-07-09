package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.mapper.FeedBackMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    private final CustomerMapper customerMapper;
    private final FeedbackService feedbackService;
    private final FeedBackMapper feedBackMapper;


    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper customerMapper,
                               FeedbackService feedbackService,
                               FeedBackMapper feedBackMapper) {
        super(repository);
        this.customerMapper = customerMapper;
        this.feedbackService = feedbackService;
        this.feedBackMapper = feedBackMapper;
    }

    //✅
    @Override
    public CustomerResponse registerCustomer(CustomerSaveRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        Customer save = repository.save(customer);
        return customerMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request) {
            Customer foundCustomer = repository.findById(request.getId())
                    .orElseThrow(
                    () -> new NotFoundException("Customer not found")
            );
            foundCustomer.setFirstName(request.getFirstName());
            foundCustomer.setLastName(request.getLastName());
            foundCustomer.setEmail(request.getEmail());
            foundCustomer.setPassword(request.getPassword());
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

  /*  public void deleteCustomer(CustomerFound request) {
        customDeleteCustomerById(request.getId());
    }*/

    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

    @Override
    public FeedbackRequest submitFeedback(FeedbackRequest feedbackRequest, Long offerOfSpecialistId) {
        FeedBack feedBack1 = feedBackMapper.feedbackDTOMapToEntity(feedbackRequest);
        if (!feedBack1.getOfferOfSpecialist().getId().equals(offerOfSpecialistId)) {
            throw new NotFoundException("Offer Of Specialist Not Found");
        }
        FeedBack feedBack = new FeedBack();
        feedBack.setFeedbackRange(feedbackRequest.getFeedbackRange());
        feedBack.setFeedbackType(feedbackRequest.getFeedbackType());
        FeedBack save = feedbackService.save(feedBack);
        return feedBackMapper.feedbackMapToDTO(save);
    }
}
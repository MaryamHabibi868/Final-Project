package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.CustomerSaveUpdateRequest;
import ir.maktab.homeservice.dto.FeedbackSubmit;
import ir.maktab.homeservice.service.base.BaseService;

public interface CustomerService extends BaseService<Customer, Long> {

    void customDeleteCustomerById(Long id);

    CustomerSaveUpdateRequest registerCustomer(CustomerSaveUpdateRequest request);

    CustomerSaveUpdateRequest updateCustomer(CustomerSaveUpdateRequest request);

    CustomerSaveUpdateRequest loginCustomer(CustomerSaveUpdateRequest request);

    FeedbackSubmit submitFeedback(
            FeedbackSubmit feedbackSubmit, Long offerOfSpecialistId);
}

package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface CustomerService extends BaseService<Customer, Long> {

    // ☑️ final check
    //✅
    CustomerResponse registerCustomer(CustomerSaveRequest request);

    // ☑️ final check
    //✅
    CustomerResponse updateCustomer(CustomerUpdateRequest request);

    // ☑️ final check
    //✅
    CustomerResponse loginCustomer(CustomerLoginRequest request);

    // ☑️ final check
    //✅
    List<CustomerResponse> findAllCustomers();

    // ☑️ final check
    //✅
    List<CustomerResponse> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
            (String firstName);

    // ☑️ final check
    //✅
    List<CustomerResponse> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
    (String lastName);
}

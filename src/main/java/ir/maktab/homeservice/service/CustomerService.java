package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface CustomerService extends BaseService<Customer, Long> {

    //✅
    CustomerResponse registerCustomer(CustomerSaveRequest request);

    //✅
    CustomerResponse updateCustomer(CustomerUpdateRequest request);

    //✅
    CustomerResponse loginCustomer(CustomerLoginRequest request);

    //✅
    List<CustomerResponse> findAllCustomers();
}

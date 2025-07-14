package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.dto.AddressResponse;
import ir.maktab.homeservice.dto.AddressSaveRequest;
import ir.maktab.homeservice.service.base.BaseService;

public interface AddressService
        extends BaseService<Address, Long> {


    AddressResponse submitAddress(AddressSaveRequest request);
}

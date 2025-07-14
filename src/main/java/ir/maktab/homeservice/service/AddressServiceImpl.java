package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.AddressResponse;
import ir.maktab.homeservice.dto.AddressSaveRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.mapper.AddressMapper;
import ir.maktab.homeservice.repository.AddressRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl
        extends BaseServiceImpl<Address, Long, AddressRepository>
        implements AddressService {

    private final CustomerService customerService;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository repository,
                              CustomerService customerService,
                              AddressMapper addressMapper) {
        super(repository);
        this.customerService = customerService;
        this.addressMapper = addressMapper;
    }


    @Override
    public AddressResponse submitAddress(AddressSaveRequest request) {
        if (repository.existsByPostalCode(request.getPostalCode())) {
            throw new DuplicatedException("Postal code already exists");
        }
        Customer foundCustomer = customerService.findById(request.getCustomerId());
        Address address = new Address();
        address.setProvince(request.getProvince());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setDescription(request.getDescription());
        address.setCustomer(foundCustomer);
        Address save = repository.save(address);
        return addressMapper.entityMapToResponse(save);
    }
}

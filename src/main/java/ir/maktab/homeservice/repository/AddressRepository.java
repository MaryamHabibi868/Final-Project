package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository
        extends BaseRepository<Address, Long> {
}

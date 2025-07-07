package ir.maktab.homeservice.repository;


import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOfCustomerRepository
        extends BaseRepository<OrderOfCustomer, Long> {
}

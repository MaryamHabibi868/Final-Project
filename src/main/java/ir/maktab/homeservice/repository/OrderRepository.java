package ir.maktab.homeservice.repository;


import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends BaseRepository<Order, Long> {


}

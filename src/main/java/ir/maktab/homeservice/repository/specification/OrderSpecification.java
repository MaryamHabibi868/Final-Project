package ir.maktab.homeservice.repository.specification;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    public static Specification<Order> hasCustomerId(Long customerId) {
        return (root, query, cb) ->
                cb.equal(root.get("customer").get("id"), customerId);
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }
}

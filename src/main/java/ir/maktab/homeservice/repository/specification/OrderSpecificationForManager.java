package ir.maktab.homeservice.repository.specification;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.OrderFilterRequestForManager;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class OrderSpecificationForManager {

    public static Specification<Order> withFilters(OrderFilterRequestForManager request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStartDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("createDate"),
                        request.getStartDate()));

            if (request.getEndDate() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("createDate"),
                        request.getEndDate()));

            if (request.getStatus() != null)
                predicates.add(cb.equal(root.get("status"), request.getStatus()));

            if (request.getHomeServiceId() != null)
                predicates.add(cb.equal(root.get("homeService").get("id"),
                        request.getHomeServiceId()));

            if (request.getCustomerId() != null)
                predicates.add(cb.equal(root.get("customer").get("id"),
                        request.getCustomerId()));

            if (request.getSpecialistId() != null) {
                Join<Order, Offer> offerJoin = root.join("offers", JoinType.INNER);
                Join<Offer, Specialist> specialistJoin = offerJoin.join("specialist",
                        JoinType.INNER);
                predicates.add(cb.equal(specialistJoin.get("id"),
                        request.getSpecialistId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

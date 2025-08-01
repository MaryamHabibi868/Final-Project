package ir.maktab.homeservice.repository.specification;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.domains.enumClasses.Role;
import ir.maktab.homeservice.dto.UserFilterRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class UserSpecification {

    public static Specification<User> filter(UserFilterRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getRole() != null) {
                predicates.add(cb.equal(root.get("role"),
                        request.getRole()));
            }

            if (request.getFirstName() != null &&
                    !request.getFirstName().isBlank()) {

                predicates.add(cb.like(cb.lower(root.get("firstName")),
                        "%" + request.getFirstName().toLowerCase() + "%"));
            }

            if (request.getLastName() != null &&
                    !request.getLastName().isBlank()) {

                predicates.add(cb.like(cb.lower(root.get("lastName")),
                        "%" + request.getLastName().toLowerCase() + "%"));
            }


            if (request.getRole() == Role.ROLE_SPECIALIST) {

                if (request.getMinScore() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("score"),
                            request.getMinScore()));
                }

                if (request.getMaxScore() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("score"),
                            request.getMaxScore()));
                }

                if (request.getServiceName() != null &&
                        !request.getServiceName().isBlank()) {

                    Join<Object, Object> join =
                            root.join("homeServices", JoinType.LEFT);
                    predicates.add(cb.equal(join.get("name"),
                            request.getServiceName()));
                    query.distinct(true);
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
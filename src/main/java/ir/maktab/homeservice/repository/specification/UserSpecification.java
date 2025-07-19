package ir.maktab.homeservice.repository.specification;

import ir.maktab.homeservice.domains.User;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class UserSpecification {

    public static Specification<User> firstNameContains(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null || firstName.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
        };
    }

    public static Specification<User> lastNameContains(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null || lastName.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
        };
    }
}
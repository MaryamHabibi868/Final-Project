package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseUserRepository<User> {

    @Query("SELECT u FROM User u WHERE TYPE(u) = :role")
    Page<User> findByRole(
            @Param("role") Class<? extends User> role, Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "(:firstName IS NOT NULL AND LOWER(u.firstName) " +
            "LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
            "OR (:lastName IS NOT NULL AND LOWER(u.lastName)" +
            " LIKE LOWER(CONCAT('%', :lastName, '%')))")
    Page<User> findByFirstNameOrLastNameContainsIgnoreCase(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable);
}

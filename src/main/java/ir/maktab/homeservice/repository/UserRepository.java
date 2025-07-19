package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseUserRepository<User> {

    @Query("SELECT u FROM User u WHERE TYPE(u) = :role")
    List<User> findByRole(@Param("role") Class<? extends User> role);

}

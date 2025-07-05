package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseUserRepository<User> {
}

package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository
        extends BaseUserRepository<Manager> {
}

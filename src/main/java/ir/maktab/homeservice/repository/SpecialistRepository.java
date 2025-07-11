package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends BaseUserRepository<Specialist> {

    Optional<Specialist> findAllByHomeServices_HomeServiceTitle(String homeServices_homeServiceTitle);
}

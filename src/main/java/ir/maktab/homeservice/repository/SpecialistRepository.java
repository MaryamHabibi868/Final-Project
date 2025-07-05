package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.repository.base.BaseUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends BaseUserRepository<Specialist> {
}

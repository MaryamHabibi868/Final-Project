package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.SubService;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServiceRepository
        extends BaseRepository<SubService, Long> {
}

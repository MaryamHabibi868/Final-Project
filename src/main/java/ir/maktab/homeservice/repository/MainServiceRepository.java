package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainServiceRepository
        extends BaseRepository<MainService , Long> {
}

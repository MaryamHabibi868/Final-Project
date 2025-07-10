package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HomeServiceRepository
        extends BaseRepository<HomeService, Long> {

    //✅
    Optional<HomeService> findAllByHomeServiceTitleIgnoreCase(String mainServiceTitle);

/*
    Optional<HomeService> findByParentServiceTitleIgnoreCase(String parentServiceTitle);
*/
}

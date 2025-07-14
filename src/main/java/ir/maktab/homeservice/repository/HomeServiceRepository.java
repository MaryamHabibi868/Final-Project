package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HomeServiceRepository
        extends BaseRepository<HomeService, Long> {


    Optional<HomeService> findAllByTitleIgnoreCase(String title);


    List<HomeService> findAllByParentService_Id(Long id);


}

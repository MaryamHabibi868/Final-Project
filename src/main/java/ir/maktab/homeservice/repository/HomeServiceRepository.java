package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HomeServiceRepository
        extends BaseRepository<HomeService, Long> {


    Optional<HomeService> findAllByTitleIgnoreCase(String title);


    Page<HomeService> findAllByParentService_Id(Long id , Pageable pageable);


}

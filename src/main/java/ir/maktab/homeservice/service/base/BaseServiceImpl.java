package ir.maktab.homeservice.service.base;

import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.repository.base.BaseRepository;
import java.io.Serializable;

public class BaseServiceImpl
        <T extends BaseEntity<ID>, ID extends Serializable,
                R extends BaseRepository<T, ID>>
        implements BaseService<T, ID>{

}

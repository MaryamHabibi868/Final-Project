package ir.maktab.homeservice.repository.base;

import ir.maktab.homeservice.domains.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>,
        ID extends Serializable >
        extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {

}

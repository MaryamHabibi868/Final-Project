package ir.maktab.homeservice.repository.base;

import ir.maktab.homeservice.domains.base.BaseEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CrudRepository
<T extends BaseEntity> {

    T save(T entity);
    List<T> saveAll(Collection<T> entities);
    Optional<T> findById(Long id);
    List<T> findAll();
    List<T> findAllById(Iterable<Long> ids);
    Long countAll();
    void deleteById(Long id);
    void deleteAllById(Iterable<Long> ids);
    boolean existsById(Long id);
    void beginTransaction();
    void commitTransaction();

}

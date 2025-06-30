package ir.maktab.homeservice.service.base;

import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.repository.base.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl
        <T extends BaseEntity,
                R extends CrudRepository<T>>
        implements BaseService<T>{

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        repository.beginTransaction();
        repository.save(entity);
        repository.commitTransaction();
        return entity;
    }

    @Override
    public List<T> saveAll(Collection<T> entities) {
        repository.beginTransaction();
        List<T> entitiesList = repository.saveAll(entities);
        repository.commitTransaction();
        return entitiesList;
    }

    @Override
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Long countAll() {
        return repository.countAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.beginTransaction();
        repository.deleteById(id);
        repository.commitTransaction();
    }

    @Override
    public void deleteAllById(Iterable<Long> ids) {
        repository.beginTransaction();
        repository.deleteAllById(ids);
        repository.commitTransaction();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}

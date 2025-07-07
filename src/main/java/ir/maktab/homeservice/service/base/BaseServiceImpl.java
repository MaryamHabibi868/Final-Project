package ir.maktab.homeservice.service.base;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl
        <T extends BaseEntity<ID>, ID extends Serializable,
                R extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }


    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void customDeleteById(ID id) {
        Optional<T> foundEntity = repository.findById(id);
        if (foundEntity.isPresent()) {
            T entity = foundEntity.get();
            T.setIsActive(false);
            repository.save(entity);
        }
        throw new NotFoundException("Customer Not Found");
    }

    @Override
    public List<T> findAll() {
        return repository.findAllByIsActiveTrue();
    }
}

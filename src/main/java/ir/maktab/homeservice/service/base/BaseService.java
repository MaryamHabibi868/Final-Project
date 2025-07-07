package ir.maktab.homeservice.service.base;

import ir.maktab.homeservice.domains.base.BaseEntity;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

public interface BaseService <T extends BaseEntity<ID> , ID extends Serializable> {

    Optional<T> findById(ID id);

    T save(T entity);
}

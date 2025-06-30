package ir.maktab.homeservice.repository.base;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public abstract class SimpleJpaRepository <T extends BaseEntity>
        implements CrudRepository<T> {

    protected Class<T> domainClass;

    public SimpleJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    protected abstract Class<T> getDomainClass();


    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public List<T> saveAll(Collection<T> entities) {
        List<T> savedEntities = new ArrayList<>();
        for (T entity : entities) {
            savedEntities.add(save(entity));
        }
        return savedEntities;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(getDomainClass(), id));
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<T> findAllById(Iterable<Long> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);

        query.where(
                cb.in(from.get("id")).value(ids)
        );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Long countAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> from = query.from(getDomainClass());
        query.select(cb.count(from));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void deleteAllById(Iterable<Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public boolean existsById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> from = query.from(getDomainClass());
        query.select(cb.count(from));
        query.where(cb.equal(from.get("id"), id));
        return entityManager.createQuery(query).getSingleResult() > 0;
    }
}

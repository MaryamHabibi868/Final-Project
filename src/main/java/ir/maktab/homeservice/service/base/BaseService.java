package ir.maktab.homeservice.service.base;

import ir.maktab.homeservice.domains.base.BaseEntity;

import java.io.Serializable;

public interface BaseService <T extends BaseEntity<ID> , ID extends Serializable> {

    //✅
    T findById(ID id);


    //✅
    T save(T entity);


    //✅
    void deleteById(ID id);


   /* //✅
    void customDeleteById(ID id);*/
}

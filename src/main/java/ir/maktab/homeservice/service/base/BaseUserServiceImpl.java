package ir.maktab.homeservice.service.base;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.repository.base.BaseUserRepository;

import java.io.Serializable;

public class BaseUserServiceImpl
        <T extends User, ID extends Serializable,
                R extends BaseUserRepository<T>>
        implements BaseUserService<T, ID> {

}

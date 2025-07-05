package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.repository.UserRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl
        extends BaseServiceImpl<User, UserRepository>
        implements PersonService{

    public PersonServiceImpl(UserRepository repository) {
        super(repository);
    }
}

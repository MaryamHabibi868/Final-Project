package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.repository.UserRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
        extends BaseServiceImpl<User, Long, UserRepository>
        implements UserService {

}

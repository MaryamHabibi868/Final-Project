package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.UserMapper;
import ir.maktab.homeservice.repository.UserRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl
        extends BaseServiceImpl<User, Long, UserRepository>
        implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository,
                           UserMapper userMapper) {
        super(repository);
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserResponse> filterByRole(String role, Pageable pageable) {
        Class<? extends User> roleClass;
        if ("Customer".equalsIgnoreCase(role)) {
            roleClass = Customer.class;
        } else if ("Specialist".equalsIgnoreCase(role)) {
            roleClass = Specialist.class;
        } else {
            throw new NotFoundException("Role Not Found");
        }

        Page<User> users = repository.findByRole(roleClass, pageable);
        return users.map(userMapper:: entityMapToResponse);
    }

    @Override
    public Page<UserResponse> findAllUsersFilterByName(
            String firstName , String lastName, Pageable pageable) {
        return repository.findByFirstNameOrLastNameContainsIgnoreCase(
                firstName , lastName, pageable)
                .map(userMapper :: entityMapToResponse);
    }


}

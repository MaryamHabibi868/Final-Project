package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService extends BaseService<User, Long> {

    Page<UserResponse> filterByRole(String role, Pageable pageable);

   Page<UserResponse> findAllByNameFilter(
           String firstName , String lastName, Pageable pageable);

   User findByEmail(String email);
}

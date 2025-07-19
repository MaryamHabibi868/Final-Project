package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;

public interface UserService extends BaseService<User, Long> {

    public List<UserResponse> filterByRole(String role);
}

package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "wallet.id", target = "walletId")
    @Mapping(expression = "java(getStatus(user))" , target = "status")
    @Mapping(expression = "java(getScore(user))" , target = "score")
    UserResponse entityMapToResponse(User user);

    default AccountStatus getStatus(User user) {
        if (user instanceof Specialist specialist) {
            return specialist.getStatus();
        } else {
            return null;
        }
    }

    default Double getScore(User user) {
        if (user instanceof Specialist specialist) {
            return specialist.getScore();
        } else {
            return null;
        }
    }

}

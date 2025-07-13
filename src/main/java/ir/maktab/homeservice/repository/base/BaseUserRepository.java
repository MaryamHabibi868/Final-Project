package ir.maktab.homeservice.repository.base;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseUserRepository<T extends User>
        extends BaseRepository<T, Long> {

    //✅
    boolean existsByEmail(String email);


    //✅
    Optional<T> findByEmailAndPassword(String email, String password);

    //✅
    <P> List<P> findUsersByIdNotNull(Class<P> clazz);

    //✅
    Optional<T> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
            (String firstName);

    //✅
    Optional<T> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
            (String lastName);
}



package ir.maktab.homeservice.repository.base;

import ir.maktab.homeservice.domains.User;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface BaseUserRepository<T extends User>
        extends BaseRepository<T, Long> {


    boolean existsByEmail(String email);


    Optional<T> findByEmailAndPassword(String email, String password);


    Optional<T> findByEmail(String email);
}



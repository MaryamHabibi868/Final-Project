package ir.maktab.homeservice.repository.base;
import ir.maktab.homeservice.domains.User;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseUserRepository<T extends User>
        extends BaseRepository<T, Long> {

}

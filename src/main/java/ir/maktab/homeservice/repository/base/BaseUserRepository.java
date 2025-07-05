package ir.maktab.homeservice.repository.base;
import ir.maktab.homeservice.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseUserRepository<T extends User>
        extends JpaRepository<T, Long>,
        JpaSpecificationExecutor<T> {

}

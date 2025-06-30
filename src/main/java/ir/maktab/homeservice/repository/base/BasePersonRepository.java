package ir.maktab.homeservice.repository.base;
import ir.maktab.homeservice.domains.Person;
import java.util.Optional;

public interface BasePersonRepository <T extends Person> extends
        CrudRepository<T> {

    Optional<T> findByUserName(String userName);
}

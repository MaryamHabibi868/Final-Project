/*
package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.Role;
import ir.maktab.homeservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository
extends BaseRepository<Role, Long> {

    boolean existsByName(String name);

    @EntityGraph(attributePaths = "authorities")
    Optional<Role> findByName(String name);
}
*/

package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Authority;
import ir.maktab.homeservice.domains.Role;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.RoleRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl
extends BaseServiceImpl<Role, Long, RoleRepository>
implements RoleService {

    private final AuthorityService authorityService;

    public RoleServiceImpl(RoleRepository repository,
                           AuthorityService authorityService) {
        super(repository);
        this.authorityService = authorityService;
    }

    @Override
//    @Transactional
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name).orElseThrow(
                () ->
                        new NotFoundException("Role with name " + name + " not found"));
    }

    @Override
    public void save(String roleName, List<String> authorityNames) {
        Set<Authority> authorities = authorityService.findAllByNames(authorityNames);
        if (authorities == null || authorities.size() != authorityNames.size()) {
            throw new RuntimeException("wrong authority");
        }
        Role byName = findByName(roleName);
        if (byName == null) {
            byName = new Role();
            byName.setName(roleName);
        }
        byName.getAuthorities().clear();
        byName.getAuthorities().addAll(authorities);
        repository.save(byName);
    }

}

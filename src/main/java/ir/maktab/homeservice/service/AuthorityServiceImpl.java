/*
package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Authority;
import ir.maktab.homeservice.repository.AuthorityRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthorityServiceImpl
        extends BaseServiceImpl<Authority, Long, AuthorityRepository>
        implements AuthorityService {

    public AuthorityServiceImpl(AuthorityRepository repository) {
        super(repository);
    }

    @Override
//    @Transactional
    public Authority save(Authority role) {
        return repository.save(role);
    }

    @Override
    public Authority findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void save(String name) {
        if (!repository.existsByName(name)) {
            repository.save(new Authority(name));
        }
    }

    @Override
    public Set<Authority> findAllByNames(List<String> authorityNames) {
        return repository.findAllByNameIsIn(authorityNames);
    }
}
*/

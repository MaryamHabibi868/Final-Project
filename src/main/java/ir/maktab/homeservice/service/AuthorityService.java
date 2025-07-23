package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Authority;
import ir.maktab.homeservice.service.base.BaseService;

import java.util.List;
import java.util.Set;

public interface AuthorityService
        extends BaseService<Authority, Long> {

    Authority save(Authority role);

    Authority findByName(String name);

    void save(String name);

    Set<Authority> findAllByNames(List<String> authorityNames);
}

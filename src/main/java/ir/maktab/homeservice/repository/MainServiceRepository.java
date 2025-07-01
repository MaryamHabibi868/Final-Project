package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.repository.base.CrudRepository;

import java.util.Optional;

public interface MainServiceRepository
        extends CrudRepository<MainService> {

    public Optional<MainService> findByMainServiceTitle(String mainServiceTitle);
}

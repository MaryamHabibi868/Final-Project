package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Person;
import ir.maktab.homeservice.repository.PersonRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;

public class PersonServiceImpl
        extends BaseServiceImpl<Person, PersonRepository>
        implements PersonService{

    public PersonServiceImpl(PersonRepository repository) {
        super(repository);
    }
}

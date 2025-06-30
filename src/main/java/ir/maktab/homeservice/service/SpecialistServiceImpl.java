package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;

public class SpecialistServiceImpl
        extends BaseServiceImpl<Specialist, SpecialistRepository>
        implements SpecialistService{

    public SpecialistServiceImpl(SpecialistRepository repository) {
        super(repository);
    }
}

package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.ManagerFound;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerServiceImpl
        extends BaseServiceImpl<Manager, Long, ManagerRepository>
        implements ManagerService {

    public ManagerServiceImpl(ManagerRepository repository) {
        super(repository);
    }

}

package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpecialistService extends BaseService<Specialist, Long> {


    SpecialistResponse registerSpecialist(
            SpecialistSaveRequest request);


    SpecialistResponse loginSpecialist(
            SpecialistLoginRequest request);


    SpecialistResponse updateSpecialistInfo(
            SpecialistUpdateInfo request);


    SpecialistResponse approveSpecialistRegistration(
            Long id);


    void addSpecialistToHomeService(
            Long specialistId, Long homeServiceId);


    void removeSpecialistFromHomeService(
            Long specialistId, Long homeServiceId);


    Page<SpecialistResponse> findAllByHomeServiceId(
            Long homeServiceId, Pageable pageable);




    Page<HomeServiceResponse> findAllHomeServicesBySpecialistId(
            Long specialistId, Pageable pageable);


    Page<SpecialistResponse> findAllByScoreIsBetween(
            Double lower, Double higher , Pageable pageable);


    Double findScoreBySpecialistId(Long specialistId);


    Page<TransactionResponse> findAllTransactionsBySpecialistId(
            Long specialistId, Pageable pageable);


    void inActivateSpecialist();
}


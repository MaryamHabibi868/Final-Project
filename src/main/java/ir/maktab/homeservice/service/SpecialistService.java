package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.base.BaseService;
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


    List<SpecialistResponse> findAllSpecialists();


    List<SpecialistResponse> findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
    (String firstName);


    List<SpecialistResponse> findAllByLastNameContainsIgnoreCaseOrderByIdAsc
    (String lastName);


    List<SpecialistResponse> findAllByHomeServiceTitle(
            String homeServiceTitle);


    List<HomeServiceResponse> findAllHomeServicesBySpecialistId(Long specialistId);


    List<SpecialistResponse> findAllByScoreIsBetween(Double lower, Double higher);


    Double findScoreBySpecialistId(Long specialistId);


    List<TransactionResponse> findAllTransactionsBySpecialistId(
            Long specialistId);


    void inActivateSpecialist();
}


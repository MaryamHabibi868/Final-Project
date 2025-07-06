package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import ir.maktab.homeservice.dto.ManagerFound;
import ir.maktab.homeservice.dto.ManagerSaveUpdateRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManagerMapper {

    ManagerSaveUpdateRequest managerMapToDTO(Manager manager);

    Manager managerDTOMapToEntity(ManagerSaveUpdateRequest managerSaveUpdateRequest);

    Manager foundManagerToEntity(ManagerFound managerFound);
}

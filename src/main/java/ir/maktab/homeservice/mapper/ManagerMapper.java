package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.ManagerFound;
import ir.maktab.homeservice.dto.ManagerSaveUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManagerMapper {

    ManagerSaveUpdateRequest managerMapToDTO(Manager manager);

    Manager managerDTOMapToEntity(ManagerSaveUpdateRequest managerSaveUpdateRequest);

    Manager foundManagerToEntity(ManagerFound managerFound);
}

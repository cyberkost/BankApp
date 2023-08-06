package com.project.bankapp.dto.mapper.manager;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import org.springframework.stereotype.Component;

@Component
public class ManagerDtoMapper {
    public ManagerDto mapEntityToDto(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        return ManagerDto.builder()
                .firstName(manager.getFirstName())
                .lastName(manager.getLastName())
                .status(manager.getStatus() != null ? manager.getStatus().name() : null)
                .description(manager.getDescription())
                .build();
    }

    public Manager mapDtoToEntity(ManagerDto managerDto) {
        if (managerDto == null) {
            throw new IllegalArgumentException("managerDto cannot be null");
        }
        return Manager.builder()
                .firstName(managerDto.getFirstName())
                .lastName(managerDto.getLastName())
                .status(managerDto.getStatus() != null ? ManagerStatus.valueOf(managerDto.getStatus()) : null)
                .description(managerDto.getDescription())
                .build();
    }
}

package com.project.bankapp.dto.mapper.manager;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import org.springframework.stereotype.Component;

/**
 * Component class responsible for mapping between Manager entities and ManagerDto Data Transfer Objects.
 */
@Component
public class ManagerDtoMapper {
    /**
     * Maps a Manager entity to a ManagerDto Data Transfer Object.
     *
     * @param manager The Manager entity to be mapped.
     * @return A ManagerDto object representing the mapped information from the entity.
     * @throws IllegalArgumentException If the input Manager entity is null.
     */
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

    /**
     * Maps a ManagerDto Data Transfer Object to a Manager entity.
     *
     * @param managerDto The ManagerDto object to be mapped.
     * @return A Manager entity representing the mapped information from the DTO.
     * @throws IllegalArgumentException If the input ManagerDto object is null.
     */
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

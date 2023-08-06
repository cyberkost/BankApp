package com.project.bankapp.service.impl;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.dto.mapper.manager.ManagerDtoMapper;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.User;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.repository.ManagerRepository;
import com.project.bankapp.service.ManagerService;
import com.project.bankapp.utils.updater.ManagerUpdater;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final ManagerUpdater managerUpdater;
    private final ManagerDtoMapper managerDtoMapper;

    @Override
    @Transactional
    public void create(ManagerDto managerDto) {
        if (managerDto == null) {
            throw new IllegalArgumentException();
        }
        Manager manager = managerDtoMapper.mapDtoToEntity(managerDto);
        managerRepository.save(manager);
    }

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public List<ManagerDto> findAllNotDeleted() {
        List<Manager> managers = managerRepository.findAllNotDeleted();
        return getDtoList(managers);
    }

    @Override
    public List<ManagerDto> findDeletedAccounts() {
        List<Manager> managers = managerRepository.findAllDeleted();
        return getDtoList(managers);
    }

    @Override
    public ManagerDto findById(String managerUuid) {
        if (managerUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(managerUuid);
        return managerDtoMapper.mapEntityToDto(
                managerRepository.findById(uuid)
                        .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid))));
    }

    @Override
    public void update(String managerUuid, ManagerDto updatedManagerDto) {
        if (managerUuid == null || updatedManagerDto == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(managerUuid);
        Manager managerUpdate = managerDtoMapper.mapDtoToEntity(updatedManagerDto);
        Manager manager = managerRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        manager = managerUpdater.update(manager, managerUpdate);
        managerRepository.save(manager);
    }

    @Override
    public void delete(String managerUuid) {
        if (managerUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(managerUuid);
        Manager manager = managerRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        manager.setDeleted(true);
        managerRepository.save(manager);
    }

    private List<ManagerDto> getDtoList(List<Manager> managers) {
        return Optional.ofNullable(managers)
                .orElse(Collections.emptyList())
                .stream()
                .map(managerDtoMapper::mapEntityToDto)
                .toList();
    }
}

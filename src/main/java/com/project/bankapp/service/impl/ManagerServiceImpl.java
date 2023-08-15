package com.project.bankapp.service.impl;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.dto.mapper.manager.ManagerDtoMapper;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.repository.ManagerRepository;
import com.project.bankapp.service.ManagerService;
import com.project.bankapp.utils.updater.ManagerUpdater;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation class responsible for managing operations related to managers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("manager created");
    }

    @Override
    @Transactional
    public void save(Manager manager) {
        log.info("saving manager into db");
        managerRepository.save(manager);
    }

    @Override
    @Transactional
    public List<Manager> findAll() {
        log.info("retrieving list of managers");
        return managerRepository.findAll();
    }

    @Override
    @Transactional
    public List<ManagerDto> findAllNotDeleted() {
        log.info("retrieving list of not deleted managers");
        List<Manager> managers = managerRepository.findAllNotDeleted();
        return getDtoList(managers);
    }

    @Override
    @Transactional
    public List<ManagerDto> findDeletedAccounts() {
        log.info("retrieving list of deleted managers");
        List<Manager> managers = managerRepository.findAllDeleted();
        return getDtoList(managers);
    }

    @Override
    @Transactional
    public ManagerDto findById(String managerUuid) {
        if (managerUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(managerUuid);
        log.info("retrieving manager by id {}", uuid);
        return managerDtoMapper.mapEntityToDto(
                managerRepository.findById(uuid)
                        .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid))));
    }

    @Override
    @Transactional
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
        log.info("updated manager id {}", uuid);
    }

    @Override
    @Transactional
    public void delete(String managerUuid) {
        if (managerUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(managerUuid);
        Manager manager = managerRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        manager.setDeleted(true);
        managerRepository.save(manager);
        log.info("deleted manager id {}", uuid);
    }

    @Override
    @Transactional
    public List<Manager> findManagersSortedByClientQuantityWhereManagerStatusIs(ManagerStatus status) {
        log.info("retrieving list of managers sorted by status {}", status);
        List<Manager> managers = managerRepository.findManagersSortedByClientCountWhereManagerStatusIs(status);
        return managers == null ? Collections.emptyList() : managers;
    }

    @Override
    public Manager getFirstManager(List<Manager> managers) {
        return managers
                .stream()
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("null"));
    }

    private List<ManagerDto> getDtoList(List<Manager> managers) {
        return Optional.ofNullable(managers)
                .orElse(Collections.emptyList())
                .stream()
                .map(managerDtoMapper::mapEntityToDto)
                .toList();
    }
}

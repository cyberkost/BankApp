package com.project.bankapp.service;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;

import java.util.List;

public interface ManagerService {

    void create(ManagerDto managerDto);

    void save(Manager manager);

    List<Manager> findAll();

    List<ManagerDto> findAllNotDeleted();

    List<ManagerDto> findDeletedAccounts();

    ManagerDto findById(String uuid);

    void update(String uuid, ManagerDto updatedManagerDto);

    void delete(String uuid);

    List<Manager> findManagersSortedByClientQuantityWhereManagerStatusIs(ManagerStatus status);

    Manager getFirstManager(List<Manager> activeManagers);
}

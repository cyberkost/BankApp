package com.project.bankapp.service;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;

import java.util.List;

/**
 * Service interface for managing manager-related operations.
 */
public interface ManagerService {
    /**
     * Creates a new manager based on the provided manager DTO.
     *
     * @param managerDto The DTO containing manager information.
     */
    void create(ManagerDto managerDto);

    /**
     * Saves a manager entity to the database.
     *
     * @param manager The manager entity to be saved.
     */
    void save(Manager manager);

    /**
     * Retrieves a list of all managers.
     *
     * @return A list of all managers.
     */
    List<Manager> findAll();

    /**
     * Retrieves a list of all managers that are not marked as deleted.
     *
     * @return A list of all managers that are not marked as deleted.
     */
    List<ManagerDto> findAllNotDeleted();

    /**
     * Retrieves a list of managers that are marked as deleted.
     *
     * @return A list of managers that are marked as deleted.
     */
    List<ManagerDto> findDeletedAccounts();

    /**
     * Retrieves a manager DTO by its UUID.
     *
     * @param uuid The UUID of the manager.
     * @return The manager DTO corresponding to the provided UUID.
     */
    ManagerDto findById(String uuid);

    /**
     * Updates a manager's information based on the provided updated manager DTO.
     *
     * @param uuid              The UUID of the manager to be updated.
     * @param updatedManagerDto The updated manager DTO.
     */
    void update(String uuid, ManagerDto updatedManagerDto);

    /**
     * Deletes a manager by its UUID.
     *
     * @param uuid The UUID of the manager to be deleted.
     */
    void delete(String uuid);

    /**
     * Retrieves a list of managers sorted by the number of clients they manage,
     * where the manager's status matches the provided status.
     *
     * @param status The status of managers to be considered.
     * @return A sorted list of managers meeting the criteria.
     */
    List<Manager> findManagersSortedByClientQuantityWhereManagerStatusIs(ManagerStatus status);

    /**
     * Retrieves the first manager from the list of active managers.
     *
     * @param activeManagers The list of active managers.
     * @return The first manager from the list, or null if the list is empty.
     */
    Manager getFirstManager(List<Manager> activeManagers);
}

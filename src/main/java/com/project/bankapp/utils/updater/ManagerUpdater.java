package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Manager;
import org.springframework.stereotype.Component;

/**
 * Component class responsible for updating properties of a Manager entity.
 */
@Component
public class ManagerUpdater {
    /**
     * Updates the properties of a Manager entity based on the information provided in the update DTO.
     *
     * @param manager       The original Manager entity to be updated.
     * @param managerUpdate The Manager entity containing updated property values.
     * @return The updated Manager entity.
     * @throws IllegalArgumentException if either the manager or managerUpdate argument is null.
     */
    public Manager update(Manager manager, Manager managerUpdate) {
        if (manager == null || managerUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(manager, managerUpdate);
    }

    /**
     * Updates specific properties of a Manager entity based on the information provided in the update DTO.
     *
     * @param manager       The original Manager entity to be updated.
     * @param managerUpdate The Manager entity containing updated property values.
     * @return The updated Manager entity.
     */
    public Manager updateProperties(Manager manager, Manager managerUpdate) {
        if (managerUpdate.getFirstName() != null) {
            manager.setFirstName(managerUpdate.getFirstName());
        }
        if (managerUpdate.getLastName() != null) {
            manager.setLastName(managerUpdate.getLastName());
        }
        if (managerUpdate.getStatus() != null) {
            manager.setStatus(managerUpdate.getStatus());
        }
        if (managerUpdate.getDescription() != null) {
            manager.setDescription(managerUpdate.getDescription());
        }
        return manager;
    }
}

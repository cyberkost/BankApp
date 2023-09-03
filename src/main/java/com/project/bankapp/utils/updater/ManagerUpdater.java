package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Manager;

public interface ManagerUpdater {
    /**
     * Updates the properties of a Manager entity based on the information provided in the update DTO.
     *
     * @param manager       The original Manager entity to be updated.
     * @param managerUpdate The Manager entity containing updated property values.
     * @return The updated Manager entity.
     * @throws IllegalArgumentException if either the manager or managerUpdate argument is null.
     */
    Manager update(Manager manager, Manager managerUpdate);

    /**
     * Updates specific properties of a Manager entity based on the information provided in the update DTO.
     *
     * @param manager       The original Manager entity to be updated.
     * @param managerUpdate The Manager entity containing updated property values.
     * @return The updated Manager entity.
     */
    Manager updateProperties(Manager manager, Manager managerUpdate);
}

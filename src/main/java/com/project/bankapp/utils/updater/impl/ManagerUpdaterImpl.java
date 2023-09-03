package com.project.bankapp.utils.updater.impl;

import com.project.bankapp.entity.Manager;
import com.project.bankapp.utils.updater.ManagerUpdater;
import org.springframework.stereotype.Component;

/**
 * Component class responsible for updating properties of a Manager entity.
 */
@Component
public class ManagerUpdaterImpl implements ManagerUpdater {

    @Override
    public Manager update(Manager manager, Manager managerUpdate) {
        if (manager == null || managerUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(manager, managerUpdate);
    }

    @Override
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

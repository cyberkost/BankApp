package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerUpdater {
    public Manager update(Manager manager, Manager managerUpdate) {
        if (manager == null || managerUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(manager, managerUpdate);
    }

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

package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.utils.updater.impl.ClientUpdaterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientUpdaterImplTest {
    ClientUpdaterImpl clientUpdater;
    Client client;
    Client clientUpdate;

    @BeforeEach
    void setUp() {
        clientUpdater = new ClientUpdaterImpl();
        client = new Client();
        client.setManagerUuid(UUID.randomUUID());
        client.setStatus(ClientStatus.ACTIVE);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setAddress("123 Main St");
        client.setPhone("555-1234");

        clientUpdate = new Client();
        clientUpdate.setManagerUuid(UUID.randomUUID());
        clientUpdate.setStatus(ClientStatus.BLOCKED);
        clientUpdate.setFirstName("Jane");
        clientUpdate.setLastName("Smith");
        clientUpdate.setEmail("jane.smith@example.com");
        clientUpdate.setAddress("456 Elm St");
        clientUpdate.setPhone("555-5678");
    }

    @Test
    void update_withValidFields_updatesClientProperties() {
        // when
        Client actual = clientUpdater.update(client, clientUpdate);
        // then
        assertEquals(clientUpdate.getManagerUuid(), actual.getManagerUuid());
        assertEquals(clientUpdate.getStatus(), actual.getStatus());
        assertEquals(clientUpdate.getFirstName(), actual.getFirstName());
        assertEquals(clientUpdate.getLastName(), actual.getLastName());
        assertEquals(clientUpdate.getEmail(), actual.getEmail());
        assertEquals(clientUpdate.getAddress(), actual.getAddress());
        assertEquals(clientUpdate.getPhone(), actual.getPhone());
    }

    @Test
    void update_withNullClient_doesNotUpdateClient_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> clientUpdater.update(null, clientUpdate));
    }

    @Test
    void update_withNullClientUpdate_doesNotUpdateClient_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> clientUpdater.update(client, null));
    }

    @Test
    void updateProperties_clientUpdateWithNullFields_updatesOnlyExistentProperties() {
        // given
        Client clientUpdate = new Client();
        clientUpdate.setManagerUuid(UUID.randomUUID());
        clientUpdate.setStatus(ClientStatus.BLOCKED);
        clientUpdate.setFirstName("Jane");
        clientUpdate.setLastName(null);
        clientUpdate.setEmail(null);
        clientUpdate.setAddress(null);
        clientUpdate.setPhone(null);
        // when
        Client actual = clientUpdater.updateProperties(client, clientUpdate);
        // then
        assertEquals(clientUpdate.getManagerUuid(), actual.getManagerUuid());
        assertEquals(clientUpdate.getStatus(), actual.getStatus());
        assertEquals(clientUpdate.getFirstName(), actual.getFirstName());
        assertEquals(client.getLastName(), actual.getLastName());
        assertEquals(client.getEmail(), actual.getEmail());
        assertEquals(client.getAddress(), actual.getAddress());
        assertEquals(client.getPhone(), actual.getPhone());
    }

    @Test
    void updateProperties_clientWithNullProperties_returnsClientWithUpdatedProperties() {
        // given
        Client client = new Client();
        // when
        Client actual = clientUpdater.updateProperties(client, clientUpdate);
        // then
        assertEquals(clientUpdate.getManagerUuid(), actual.getManagerUuid());
        assertEquals(clientUpdate.getStatus(), actual.getStatus());
        assertEquals(clientUpdate.getFirstName(), actual.getFirstName());
        assertEquals(clientUpdate.getLastName(), actual.getLastName());
        assertEquals(clientUpdate.getEmail(), actual.getEmail());
        assertEquals(clientUpdate.getAddress(), actual.getAddress());
        assertEquals(clientUpdate.getPhone(), actual.getPhone());
    }

    @Test
    void updateProperties_updateWithNullProperties_doesNotUpdateClientProperties() {
        // given
        Client clientUpdate1 = new Client();
        // when
        Client actual = clientUpdater.updateProperties(client, clientUpdate1);
        // then
        assertEquals(client.getManagerUuid(), actual.getManagerUuid());
        assertEquals(client.getStatus(), actual.getStatus());
        assertEquals(client.getFirstName(), actual.getFirstName());
        assertEquals(client.getLastName(), actual.getLastName());
        assertEquals(client.getEmail(), actual.getEmail());
        assertEquals(client.getAddress(), actual.getAddress());
        assertEquals(client.getPhone(), actual.getPhone());
    }
}
package com.project.bankapp.repository;

import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing and managing client data in the database.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    /**
     * Retrieves a list of clients that have been marked as deleted.
     *
     * @return A list of clients marked as deleted.
     */
    @Query("SELECT cl FROM Client cl WHERE cl.isDeleted = true")
    List<Client> findAllDeleted();

    /**
     * Retrieves a list of clients that have not been marked as deleted.
     *
     * @return A list of clients not marked as deleted.
     */
    @Query("SELECT cl FROM Client cl WHERE cl.isDeleted = false")
    List<Client> findAllNotDeleted();

    /**
     * Retrieves a list of clients with a specific status.
     *
     * @param status The status of clients to retrieve.
     * @return A list of clients with the specified status.
     */
    List<Client> findClientsByStatusIs(ClientStatus status);

    /**
     * Checks if the status of a client with the provided UUID is blocked.
     *
     * @param uuid The UUID of the client to check.
     * @return True if the client's status is blocked, false otherwise.
     */
    @Query("SELECT CASE WHEN cl.status = 'BLOCKED' THEN TRUE ELSE FALSE END " +
            "FROM Client cl " +
            "WHERE cl.uuid = :uuid")
    Boolean isClientStatusBlocked(@Param("uuid") UUID uuid);
}

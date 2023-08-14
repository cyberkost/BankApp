package com.project.bankapp.repository;

import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("SELECT cl FROM Client cl WHERE cl.isDeleted = true")
    List<Client> findAllDeleted();

    @Query("SELECT cl FROM Client cl WHERE cl.isDeleted = false")
    List<Client> findAllNotDeleted();

    List<Client> findClientsByStatusIs(ClientStatus status);

    @Query("SELECT CASE WHEN cl.status = 'BLOCKED' THEN TRUE ELSE FALSE END " +
            "FROM Client cl " +
            "WHERE cl.uuid = :uuid")
    Boolean isClientStatusBlocked(@Param("uuid") UUID uuid);
}

package com.project.bankapp.repository;

import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing database operations related to managers.
 */
@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    /**
     * Retrieves a list of managers that are not marked as deleted.
     *
     * @return A list of managers that are active (not marked as deleted).
     */
    @Query("SELECT mg FROM Manager mg WHERE mg.isDeleted = false")
    List<Manager> findAllNotDeleted();

    /**
     * Retrieves a list of managers that are marked as deleted.
     *
     * @return A list of managers that are deleted.
     */
    @Query("SELECT mg FROM Manager mg WHERE mg.isDeleted = true")
    List<Manager> findAllDeleted();

    /**
     * Retrieves a list of managers sorted by the number of associated clients, where the manager status matches the provided status.
     *
     * @param status The status of managers to consider for sorting.
     * @return A list of managers sorted by the number of associated clients in ascending order.
     */
    @Query("SELECT mg FROM Manager mg " +
            "JOIN Client cl ON cl.managerUuid = mg.uuid " +
            "WHERE mg.status = :status " +
            "GROUP BY mg " +
            "ORDER BY COUNT(cl) ASC")
    List<Manager> findManagersSortedByClientCountWhereManagerStatusIs(@Param("status") ManagerStatus status);
}

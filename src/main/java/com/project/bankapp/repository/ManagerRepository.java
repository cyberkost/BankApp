package com.project.bankapp.repository;

import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    @Query("SELECT mg FROM Manager mg WHERE mg.isDeleted = false")
    List<Manager> findAllNotDeleted();

    @Query("SELECT mg FROM Manager mg WHERE mg.isDeleted = true")
    List<Manager> findAllDeleted();

    @Query("SELECT mg FROM Manager mg " +
            "JOIN Client cl ON cl.managerUuid = mg.uuid " +
            "WHERE mg.status = :status " +
            "GROUP BY mg " +
            "ORDER BY COUNT(cl) ASC")
    List<Manager> findManagersSortedByClientCountWhereManagerStatusIs(@Param("status") ManagerStatus status);

}

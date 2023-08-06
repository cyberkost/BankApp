package com.project.bankapp.repository;

import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("SELECT ac FROM Account ac WHERE ac.isDeleted = false")
    List<Account> findAllNotDeleted();

    @Query("SELECT ac FROM Account ac WHERE ac.isDeleted = true")
    List<Account> findAllDeleted();

    List<Account> findAccountsByStatus(AccountStatus status);

    List<Account> findAccountsByClientUuid(UUID uuid);
}

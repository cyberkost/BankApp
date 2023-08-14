package com.project.bankapp.repository;

import com.project.bankapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findTransactionsByDebitAccountUuid(UUID uuid);

    List<Transaction> findTransactionsByCreditAccountUuid(UUID uuid);

    @Query("SELECT tr FROM Transaction tr " +
            "JOIN Account ac ON ac.uuid = tr.debitAccountUuid " +
            "OR ac.uuid = tr.creditAccountUuid " +
            "JOIN Client cl ON cl.uuid = ac.clientUuid " +
            "WHERE cl.uuid = :clientUuid")
    List<Transaction> findAllTransactionsWhereClientIdIs(@Param("clientUuid") UUID clientUuid);

}

package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.entity.enums.AccountType;
import com.project.bankapp.entity.enums.CurrencyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountUpdaterTest {
    AccountUpdater accountUpdater;
    Account account;
    Account accountUpdate;

    @BeforeEach
    void setUp() {
        accountUpdater = new AccountUpdater();
        account = new Account();
        account.setClientUuid(UUID.randomUUID());
        account.setName("Test Account");
        account.setType(AccountType.CURRENT);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.valueOf(100));
        account.setCurrencyCode(CurrencyCode.EUR);

        accountUpdate = new Account();
        accountUpdate.setClientUuid(UUID.randomUUID());
        accountUpdate.setName("Updated Account");
        accountUpdate.setType(AccountType.SAVINGS);
        accountUpdate.setStatus(AccountStatus.BLOCKED);
        accountUpdate.setBalance(BigDecimal.valueOf(200));
        accountUpdate.setCurrencyCode(CurrencyCode.GBP);
    }

    @Test
    void update_withValidFields_updatesAccountProperties() {
        // when
        Account actual = accountUpdater.update(account, accountUpdate);
        // then
        assertEquals(accountUpdate.getClientUuid(), actual.getClientUuid());
        assertEquals(accountUpdate.getName(), actual.getName());
        assertEquals(accountUpdate.getType(), actual.getType());
        assertEquals(accountUpdate.getStatus(), actual.getStatus());
        assertEquals(accountUpdate.getBalance(), actual.getBalance());
        assertEquals(accountUpdate.getCurrencyCode(), actual.getCurrencyCode());
    }

    @Test
    void update_withNullAccountUpdate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountUpdater.update(account, null));
    }

    @Test
    void update_withNullAccount_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountUpdater.update(null, accountUpdate));
    }

    @Test
    void updateProperties_accountUpdateWithNullFields_updatesOnlyExistentProperties() {
        // given
        Account account = new Account();
        account.setClientUuid(UUID.randomUUID());
        account.setName("Test Account");
        account.setType(AccountType.CURRENT);
        account.setStatus(null);
        account.setBalance(null);
        account.setCurrencyCode(null);
        // when
        Account actual = accountUpdater.updateProperties(account, accountUpdate);
        // then
        assertEquals(accountUpdate.getClientUuid(), actual.getClientUuid());
        assertEquals(accountUpdate.getName(), actual.getName());
        assertEquals(accountUpdate.getType(), actual.getType());
        assertEquals(account.getStatus(), actual.getStatus());
        assertEquals(account.getBalance(), actual.getBalance());
        assertEquals(account.getCurrencyCode(), actual.getCurrencyCode());
    }

    @Test
    void updateProperties_accountWithNullProperties_returnsAccountWithUpdatedProperties() {
        // given
        Account account = new Account();
        // when
        Account actual = accountUpdater.updateProperties(account, accountUpdate);
        // then
        assertEquals(accountUpdate.getClientUuid(), actual.getClientUuid());
        assertEquals(accountUpdate.getName(), actual.getName());
        assertEquals(accountUpdate.getType(), actual.getType());
        assertEquals(accountUpdate.getStatus(), actual.getStatus());
        assertEquals(accountUpdate.getBalance(), actual.getBalance());
        assertEquals(accountUpdate.getCurrencyCode(), actual.getCurrencyCode());
    }

    @Test
    void updateProperties_updateWithNullProperties_doesNotUpdateAccountProperties() {
        // given
        Account accountUpdate = new Account();
        // when
        Account actual = accountUpdater.updateProperties(account, accountUpdate);
        // then
        assertEquals(account.getClientUuid(), actual.getClientUuid());
        assertEquals(account.getName(), actual.getName());
        assertEquals(account.getType(), actual.getType());
        assertEquals(account.getStatus(), actual.getStatus());
        assertEquals(account.getBalance(), actual.getBalance());
        assertEquals(account.getCurrencyCode(), actual.getCurrencyCode());
    }
}
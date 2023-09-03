package com.project.bankapp.utils.updater.impl;

import com.project.bankapp.entity.Account;
import com.project.bankapp.utils.updater.AccountUpdater;
import org.springframework.stereotype.Component;

/**
 * Component responsible for updating account properties based on the provided account update.
 */
@Component
public class AccountUpdaterImpl implements AccountUpdater {

    @Override
    public Account update(Account account, Account accountUpdate) {
        if (account == null || accountUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(account, accountUpdate);
    }

    @Override
    public Account updateProperties(Account account, Account accountUpdate) {
        if (accountUpdate.getClientUuid() != null) {
            account.setClientUuid(accountUpdate.getClientUuid());
        }
        if (accountUpdate.getName() != null) {
            account.setName(accountUpdate.getName());
        }
        if (accountUpdate.getType() != null) {
            account.setType(accountUpdate.getType());
        }
        if (accountUpdate.getStatus() != null) {
            account.setStatus(accountUpdate.getStatus());
        }
        if (accountUpdate.getBalance() != null) {
            account.setBalance(accountUpdate.getBalance());
        }
        if (accountUpdate.getCurrencyCode() != null) {
            account.setCurrencyCode(accountUpdate.getCurrencyCode());
        }
        return account;
    }
}

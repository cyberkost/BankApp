package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountUpdater {
    public Account update(Account account, Account accountUpdate) {
        if (account == null || accountUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(account, accountUpdate);
    }

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

package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Account;
import org.springframework.stereotype.Component;

/**
 * Component responsible for updating account properties based on the provided account update.
 */
@Component
public class AccountUpdater {
    /**
     * Updates the given account using the information provided in the account update.
     *
     * @param account       The account to be updated.
     * @param accountUpdate The account update containing new property values.
     * @return The updated account instance.
     * @throws IllegalArgumentException if either the account or account update is null.
     */
    public Account update(Account account, Account accountUpdate) {
        if (account == null || accountUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(account, accountUpdate);
    }

    /**
     * Updates the account properties with the corresponding values from the account update.
     *
     * @param account       The account to be updated.
     * @param accountUpdate The account update containing new property values.
     * @return The updated account instance.
     */
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

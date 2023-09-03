package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Account;

public interface AccountUpdater {
    /**
     * Updates the given account using the information provided in the account update.
     *
     * @param account       The account to be updated.
     * @param accountUpdate The account update containing new property values.
     * @return The updated account instance.
     * @throws IllegalArgumentException if either the account or account update is null.
     */
    Account update(Account account, Account accountUpdate);

    /**
     * Updates the account properties with the corresponding values from the account update.
     *
     * @param account       The account to be updated.
     * @param accountUpdate The account update containing new property values.
     * @return The updated account instance.
     */
    Account updateProperties(Account account, Account accountUpdate);
}

package com.project.bankapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@WebMvcTest(AccountController.class)
@SpringJUnitConfig
class AccountControllerTest {

    @Test
    public void createAccount() throws Exception {

    }

    @Test
    void testCreateAccount() {
    }

    @Test
    void findAllAccounts() {
    }

    @Test
    void findAccountByUuid() {
    }

    @Test
    void findAllAccountsByStatus() {
    }

    @Test
    void findAllAccountsByClientUuid() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}
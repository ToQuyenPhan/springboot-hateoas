package com.example.account.accountAPI;

import com.example.account.accountAPI.models.Account;
import com.example.account.accountAPI.repositories.IAccountRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback
public class AccountRepositoryTests {
    @Autowired
    private IAccountRepository repo;

    @Test
    public void testAddAccount(){
        Account account = new Account("123456789", 100);
        Account savedAccount = repo.save(account);
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isGreaterThan(0);
    }

    @Test
    public void testDepositAmount(){
        Account account = new Account("123456789", 100);
        Account savedAccount = repo.save(account);
        repo.updateBalance(50, savedAccount.getId());
        Account updatedAccount = repo.findById(savedAccount.getId()).get();
        assertThat(updatedAccount.getBalance()).isEqualTo(150);
    }
}

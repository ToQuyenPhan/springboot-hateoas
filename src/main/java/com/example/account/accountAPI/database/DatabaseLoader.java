package com.example.account.accountAPI.database;

import com.example.account.accountAPI.models.Account;
import com.example.account.accountAPI.repositories.IAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseLoader {
    private IAccountRepository repo;

    public DatabaseLoader(IAccountRepository repo) {
        this.repo = repo;
    }

    @Bean
    public CommandLineRunner initDatabase(){
        return args -> {
            Account account1 = new Account("123456789", 100);
            Account account2 = new Account("987654321", 50);
            Account account3 = new Account("123987654", 1000);
            repo.saveAll(List.of(account1, account2, account3));
            System.out.println("Sample database initialized.");
        };
    }
}

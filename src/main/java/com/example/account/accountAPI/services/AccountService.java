package com.example.account.accountAPI.services;

import com.example.account.accountAPI.exeptions.AccountNotFoundException;
import com.example.account.accountAPI.models.Account;
import com.example.account.accountAPI.repositories.IAccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private IAccountRepository repo;

    public AccountService(IAccountRepository repo) {
        this.repo = repo;
    }

    public List<Account> listAll(){
        return repo.findAll();
    }

    public Account get(Integer id){
        return repo.findById(id).get();
    }

    public Account save(Account account){
        return repo.save(account);
    }

    public Account deposit(float amount, Integer id){
        repo.updateBalance(amount, id);
        return repo.findById(id).get();
    }

    public Account withdraw(float amount, Integer id){
        repo.updateBalance(-amount, id);
        return repo.findById(id).get();
    }

    public void delete(Integer id) throws AccountNotFoundException {
        if(!repo.existsById(id)){
            throw new AccountNotFoundException();
        }
        repo.deleteById(id);
    }
}

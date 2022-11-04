package com.example.account.accountAPI.controllers;

import com.example.account.accountAPI.exeptions.AccountNotFoundException;
import com.example.account.accountAPI.models.Account;
import com.example.account.accountAPI.models.AccountModelAssembler;
import com.example.account.accountAPI.models.Amount;
import com.example.account.accountAPI.services.AccountService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService service;
    private AccountModelAssembler assembler;

    public AccountController(AccountService service, AccountModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Account>>> listAll(){
        List<Account> listAccounts = service.listAll();
        if(listAccounts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        CollectionModel<EntityModel<Account>> collectionModel = assembler.toCollectionModel(listAccounts);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Account>> getOne(@PathVariable("id") Integer id){
        try{
            Account account = service.get(id);
            EntityModel<Account> entityModel = assembler.toModel(account);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Account>> add(@RequestBody Account account){
        Account savedAccount = service.save(account);
        EntityModel<Account> entityModel = assembler.toModel(account);
        return ResponseEntity.created(linkTo(methodOn(AccountController.class).getOne(savedAccount.getId())).toUri())
                .body(entityModel);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Account>> replace(@RequestBody Account account){
        Account updatedAccount = service.save(account);
        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<EntityModel<Account>> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount){
        Account updatedAccount = service.deposit(amount.getAmount(), id);
        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<EntityModel<Account>> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount){
        Account updatedAccount = service.withdraw(amount.getAmount(), id);
        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }catch (AccountNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}

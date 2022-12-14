package com.example.account.accountAPI.models;

import com.example.account.accountAPI.controllers.AccountController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    @Override
    public EntityModel<Account> toModel(Account account) {
        EntityModel<Account> accountModel = EntityModel.of(account);
        accountModel.add(linkTo(methodOn(AccountController.class).getOne(account.getId())).withSelfRel());
        accountModel.add(linkTo(methodOn(AccountController.class).deposit(account.getId(), null)).withRel("deposits"));
        accountModel.add(linkTo(methodOn(AccountController.class).withdraw(account.getId(), null)).withRel("withdrawals"));
        accountModel.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
        return accountModel;
    }
}

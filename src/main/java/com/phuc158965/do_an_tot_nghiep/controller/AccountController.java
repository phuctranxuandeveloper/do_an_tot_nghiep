package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping
    public ResponseEntity<?> getAllAccount(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Account> assembler
    ){
        Page<Account> accounts = accountService.findAllAccount(page, size);
        PagedModel<EntityModel<Account>> accountPagedModel = assembler.toModel(accounts, account ->
                EntityModel.of(account,
                        WebMvcLinkBuilder.linkTo(methodOn(AccountController.class).getAccountById(account.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(AccountController.class).getUserByAccountId(account.getId())).withRel("users")));
        return new ResponseEntity<>(accountPagedModel, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id){
        Account account = accountService.findAccountById(id);
        EntityModel<Account> rs = EntityModel.of(account);
        rs.add(linkTo(AccountController.class).slash(id).withSelfRel());
        rs.add(linkTo(methodOn(AccountController.class).getUserByAccountId(id)).withRel("users"));
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping("{id}/users")
    public ResponseEntity<?> getUserByAccountId(@PathVariable Integer id){
        Account account = accountService.findAccountById(id);
        User user = account.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

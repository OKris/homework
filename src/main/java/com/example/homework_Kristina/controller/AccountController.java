package com.example.homework_Kristina.controller;

import com.example.homework_Kristina.dto.AccountDto;
import com.example.homework_Kristina.entity.Account;
import com.example.homework_Kristina.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody AccountDto account){
        return accountService.createAccount(account);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.findAccountById(id);
    }

    @PutMapping("/accounts/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody AccountDto account){
        return accountService.updateAccountById(id, account);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable Long id){
        return accountService.deleteAccount(id);
    }
}

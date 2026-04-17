package com.example.homework_Kristina.service;

import com.example.homework_Kristina.entity.Account;
import com.example.homework_Kristina.repository.AccountRepository;
import com.example.homework_Kristina.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void validateAccount(Account account) {
        if (account.getName() == null || account.getName().isEmpty()) {
            throw new RuntimeException("Name cannot be empty");
        }

        if (!Validations.isValidName(account.getName())) {
            throw new RuntimeException("Name is not valid, no special characters should be used");
        }

        if (account.getPhoneNr() != null && !account.getPhoneNr().isBlank()) {
            if (accountRepository.findByPhoneNr(account.getPhoneNr()).isPresent()){
                //throw new RuntimeException("Phone number already exists");
            }

            String number = Validations.phoneNumberConversion(account.getPhoneNr(), "EE");
            account.setPhoneNr(number);
        }
    }

    public Account createAccount(Account account) {
       validateAccount(account);
       return accountRepository.save(account);
    }


    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateAccountById(Long id, Account account) {
        Account excistingAccount = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        validateAccount(account);
        excistingAccount.setName(account.getName());
        excistingAccount.setPhoneNr(account.getPhoneNr());

        return accountRepository.save(excistingAccount);
    }


    public ResponseEntity<Void> deleteAccount(Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

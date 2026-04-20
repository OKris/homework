package com.example.homework_Kristina.service;

import com.example.homework_Kristina.dto.AccountDto;
import com.example.homework_Kristina.entity.Account;
import com.example.homework_Kristina.repository.AccountRepository;
import com.example.homework_Kristina.util.Validations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    public void validateName(AccountDto account) {
        if (account.getName() == null || account.getName().trim().isEmpty()) {
            throw new RuntimeException("Name cannot be empty");
        }

        if (!Validations.isValidName(account.getName())) {
            throw new RuntimeException("Name is not valid, no special characters should be used");
        }
    }

    public void validateCreateAccount(AccountDto account) {
        validateName(account);

        if (account.getPhoneNr() != null && !account.getPhoneNr().isBlank()) {
            if (accountRepository.findByPhoneNr(account.getPhoneNr()).isPresent()){
                throw new RuntimeException("Phone number already exists");
            }

            String number = Validations.phoneNumberConversion(account.getPhoneNr(), "EE");
            account.setPhoneNr(number);
        }
    }

    public void validateUpdateAccount(Long id, AccountDto account) {
        validateName(account);

        if (account.getPhoneNr() != null && !account.getPhoneNr().isBlank()) {
            Optional<Account> existing = accountRepository.findByPhoneNr(account.getPhoneNr());
            if (existing.isPresent() && !existing.get().getId().equals(id)){
                throw new RuntimeException("Phone number already exists");
            }

            String number = Validations.phoneNumberConversion(account.getPhoneNr(), "EE");
            account.setPhoneNr(number);
        }
    }

    public Account createAccount(AccountDto account) {
        validateCreateAccount(account);
        Account newAccount = modelMapper.map(account, Account.class);
        return accountRepository.save(newAccount);
    }


    public Account findAccountById(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException( "Account not found"));
    }

    public Account updateAccountById(Long id, AccountDto account) throws AccountNotFoundException {
        Account excistingAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found"));

        validateUpdateAccount(id, account);
        excistingAccount.setName(account.getName());
        excistingAccount.setPhoneNr(account.getPhoneNr());
        return accountRepository.save(excistingAccount);
    }


    public ResponseEntity<Void> deleteAccount(Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

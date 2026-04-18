package com.example.homework_Kristina.service;

import com.example.homework_Kristina.dto.AccountDto;
import com.example.homework_Kristina.entity.Account;
import com.example.homework_Kristina.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountService accountService;

    private AccountDto accountDto;
    private Account account;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 2L;
        accountDto = new AccountDto();
        accountDto.setName("John Doe");

        account = new Account();
        account.setId(1L);
        account.setName("John Doe");
        account.setCreatedDatetime(LocalDateTime.parse("2026-04-18T14:24:47.91912088"));
        account.setModifiedDatetime(LocalDateTime.parse("2026-04-18T14:24:47.91912088"));
    }

    @Test
    void validateAccount() {
    }

    @Test
    void createAccountWithName() {
        when(modelMapper.map(accountDto, Account.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.createAccount(accountDto);

        assertEquals(accountDto.getName(), result.getName());
        assertNull(result.getPhoneNr());
        verify(accountRepository).save(account);
    }

    @Test
    void createAccountWithNameAndPhoneNr() {
        accountDto.setPhoneNr("37258539087");
        account.setPhoneNr("+37258539087");

        when(modelMapper.map(accountDto, Account.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.createAccount(accountDto);

        assertEquals(accountDto.getName(), result.getName());
        assertEquals(accountDto.getPhoneNr(), result.getPhoneNr());
        verify(accountRepository).save(account);
    }

    @Test
    void createAccountWithFaultyName() {
        accountDto.setName("-John Doe5");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(accountDto);
        });
        assertEquals("Name is not valid, no special characters should be used", ex.getMessage());
    }

    @Test
    void createAccountWithEmptyName() {
        accountDto.setName(" ");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(accountDto);
        });
        assertEquals("Name cannot be empty", ex.getMessage());
    }



    @Test
    void findAccountById() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        Account result = accountService.findAccountById(account.getId());
        assertEquals(account.getId(), result.getId());
        verify(accountRepository).findById(account.getId());
    }

    @Test
    void findAccountByWrongId() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.findAccountById(id);
        });
        assertEquals("Account not found", ex.getMessage());
    }

    @Test
    void updateAccountById() {
        accountDto.setName("Zeus O'Connel");
        accountDto.setPhoneNr("56456783");

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.updateAccountById(id, accountDto);
        assertEquals(accountDto.getName(), result.getName());
        assertEquals(accountDto.getPhoneNr(), result.getPhoneNr());
        verify(accountRepository).save(account);
    }

    @Test
    void updateAccountByIdWithOnlyName() {
        accountDto.setName("Mary");

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.updateAccountById(id, accountDto);
        assertEquals(accountDto.getName(), result.getName());
        assertNull(result.getPhoneNr());
        verify(accountRepository).save(account);
    }

    @Test
    void updateAccountWithWrongId() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.updateAccountById(id, accountDto);
        });
        assertEquals("Account not found", ex.getMessage());
    }

    @Test
    void deleteAccount() {
        doNothing().when(accountRepository).deleteById(account.getId());
        accountService.deleteAccount(account.getId());
        verify(accountRepository).deleteById(account.getId());
    }
}
package com.example.homework_Kristina.repository;

import com.example.homework_Kristina.entity.Account;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByPhoneNr(@Pattern(regexp = "^\\+[0-9]{7,15}$") String phoneNr);
}

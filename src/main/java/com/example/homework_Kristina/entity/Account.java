package com.example.homework_Kristina.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ' -]{2,50}$")
    @NotBlank
    private String name;

    @Pattern(regexp = "^\\+[0-9]{7,15}$")
    @Column(unique=true, length = 16)
    private String phoneNr;

    @CreatedDate
    @Column(name = "created_date_time")
    private LocalDateTime createdDatetime;

    @LastModifiedDate
    @Column(name = "modified_date_time")
    private LocalDateTime modifiedDatetime;
}
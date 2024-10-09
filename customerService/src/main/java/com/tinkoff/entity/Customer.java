package com.tinkoff.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Should be initialized")
    private Long id;

    @NotNull(message = "Should be initialized")
    private String name;

    @NotNull(message = "Should be initialized")
    private String surname;
}

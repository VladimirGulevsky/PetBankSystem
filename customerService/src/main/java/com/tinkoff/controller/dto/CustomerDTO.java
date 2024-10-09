package com.tinkoff.controller.dto;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CustomerDTO {

    @NotNull(message = "Should be initialized")
    private String name;

    @NotNull (message = "Should be initialized")
    private String surname;
}

package com.tinkoff.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BillDTO {

    private Long id;

    @Min(value = 0, message = "Should be more than zero")
    private BigDecimal sum;

    private Long customerID;

}

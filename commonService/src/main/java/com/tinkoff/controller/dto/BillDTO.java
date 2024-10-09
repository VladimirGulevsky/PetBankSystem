package com.tinkoff.controller.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class BillDTO {

    @NotNull(message = "Should be initialized")
    private Long id;

    @Min(value = 0, message = "Should be more than zero")
    private BigDecimal sum;

    @NotNull(message = "Should be initialized")
    private Long customerID;

}

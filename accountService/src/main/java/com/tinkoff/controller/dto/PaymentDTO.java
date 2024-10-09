package com.tinkoff.controller.dto;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class PaymentDTO {

    @Min(value = 0, message = "Should be more than zero")
    private BigDecimal payment;

    public BigDecimal getPayment() {
        return payment;
    }
}

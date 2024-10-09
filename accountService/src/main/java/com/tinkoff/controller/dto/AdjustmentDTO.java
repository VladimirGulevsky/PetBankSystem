package com.tinkoff.controller.dto;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class AdjustmentDTO {

    @Min(value = 0, message = "Should be more than zero")
    private BigDecimal adjustment;

    public BigDecimal getAdjustment() {
        return adjustment;
    }

}

package com.tinkoff.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BillTransferDTO {

    @NotNull(message = "Should be initialized")
    private final BillDTO decreasedBill;

    @NotNull(message = "Should be initialized")
    private final BillDTO increasedBill;

}

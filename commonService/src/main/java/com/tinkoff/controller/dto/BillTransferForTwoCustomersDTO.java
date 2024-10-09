package com.tinkoff.controller.dto;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BillTransferForTwoCustomersDTO {

    @NotNull(message = "Should be initialized")
    private Long firstCustomerID;

    @NotNull(message = "Should be initialized")
    private Long secondCustomerID;

    @NotNull(message = "Should be initialized")
    private Long decreasedBillID;

    @NotNull(message = "Should be initialized")
    private Long increasedBillID;

}

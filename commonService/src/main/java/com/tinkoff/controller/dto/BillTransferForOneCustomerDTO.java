package com.tinkoff.controller.dto;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BillTransferForOneCustomerDTO {

    @NotNull(message = "Should be initialized")
    private Long customerID;

    @NotNull(message = "Should be initialized")
    private Long decreasedBillID;

    @NotNull(message = "Should be initialized")
    private Long increasedBillID;

}

package com.tinkoff.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BillTransferDTO {

    private final BillDTO decreasedBill;
    private final BillDTO increasedBill;

}

package com.tinkoff.controller;

import com.tinkoff.controller.dto.BillTransferForOneCustomerDTO;
import com.tinkoff.controller.dto.BillTransferForTwoCustomersDTO;
import com.tinkoff.controller.dto.CustomerDTO;
import com.tinkoff.exceptions.NotFoundBillException;
import com.tinkoff.exceptions.NotEnoughMoneyException;
import com.tinkoff.service.CommonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
public class CommonController {

    private final CommonService commonService;

    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @RequestMapping(value = "/common/info/{id}", method = RequestMethod.GET)
    public CustomerDTO getInfo(@PathVariable @NotNull Long id) {
        return commonService.getCustomerInfo(id);
    }

    @RequestMapping(value = "/common/transfer-for-one-customer/{transfer}", method = RequestMethod.PUT)
    public void commitTransferForOneCustomer(@PathVariable @NotNull BigDecimal transfer,
                                             @RequestBody @Valid BillTransferForOneCustomerDTO billTransferForOneCustomerDTO) throws NotFoundBillException, NotEnoughMoneyException {
        commonService.commitTransferForOneCustomer(billTransferForOneCustomerDTO.getCustomerID(),
                                                    billTransferForOneCustomerDTO.getDecreasedBillID(),
                                                    billTransferForOneCustomerDTO.getIncreasedBillID(),
                                                    transfer);
    }

    @RequestMapping(value = "/common/transfer-for-two-customers/{transfer}", method = RequestMethod.PUT)
    public void commitTransferForTwoCustomer(@PathVariable @NotNull BigDecimal transfer,
                                             @RequestBody @Valid BillTransferForTwoCustomersDTO billTransferForTwoCustomersDTO) throws NotFoundBillException, NotEnoughMoneyException {
        commonService.commitTransferForTwoCustomers(billTransferForTwoCustomersDTO.getFirstCustomerID(),
                                                    billTransferForTwoCustomersDTO.getDecreasedBillID(),
                                                    billTransferForTwoCustomersDTO.getSecondCustomerID(),
                                                    billTransferForTwoCustomersDTO.getIncreasedBillID(),
                                                    transfer);
    }
}

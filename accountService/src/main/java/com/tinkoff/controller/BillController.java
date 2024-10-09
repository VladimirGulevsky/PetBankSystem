package com.tinkoff.controller;

import com.tinkoff.controller.dto.*;
import com.tinkoff.entity.Bill;
import com.tinkoff.exceptions.NotFoundBillException;
import com.tinkoff.exceptions.NotEnoughMoneyException;
import com.tinkoff.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BillController {


    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @RequestMapping(value = "/bill/create/{customerID}", method = RequestMethod.POST)
    public Bill createBill(@PathVariable @NotNull(message = "Should initialized") Long customerID,
                           @RequestBody @Valid BillDTO bill) {
        return billService.createBill(customerID, bill.getSum());
    }

    @RequestMapping(value = "/bill/{id}", method = RequestMethod.GET)
    public Bill getBillById(@PathVariable Long id) throws NotFoundBillException {
        return billService.getBill(id);
    }

    @RequestMapping(value = "bill/delete/{id}", method = RequestMethod.POST)
    public void deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
    }


    @RequestMapping(value = "bill/adjustment/{id}", method = RequestMethod.PUT)
    public Bill createAdjustment(@PathVariable @NotNull(message = "Should be initialized") Long id,
                                 @RequestBody @Valid AdjustmentDTO adjustment) {

        return billService.commitAdjustment(id, adjustment.getAdjustment());
    }

    @NotNull
    @RequestMapping(value = "/bill/payment/{id}", method = RequestMethod.PUT)
    public Bill createPayment(@PathVariable @NotNull(message = "Should be initialized") Long id,
                              @RequestBody @Valid PaymentDTO payment) throws NotEnoughMoneyException {
        return billService.commitPayment(id, payment.getPayment());
    }

    @RequestMapping(value = "/bill/get-by-customer-id/{customerID}", method = RequestMethod.GET)
    public List<BillDTO> getBillByCustomerID(@PathVariable Long customerID) {

        List<Bill> billList = billService.getByCustomerID(customerID);
        ArrayList<BillDTO> billDTOList = new ArrayList<>();
        for (int i = 0; i < billList.size(); i++) {
            BillDTO billDTO = new BillDTO(billList.get(i).getId(), billList.get(i).getSum(), billList.get(i).getCustomerID());
            billDTOList.add(billDTO);
        }
        return billDTOList;
    }

    @RequestMapping(value = "/bill/transfer", method = RequestMethod.POST)
    public void commitTransfer(@RequestBody BillTransferDTO billTransferDTO) throws NotFoundBillException {

        BillDTO decreasedBill = billTransferDTO.getDecreasedBill();
        BillDTO increasedBill = billTransferDTO.getIncreasedBill();
        Long decreasedBillID = decreasedBill.getId();
        Long increasedBillID = increasedBill.getId();
        BigDecimal decreasedSum = decreasedBill.getSum();
        BigDecimal increasedSum = increasedBill.getSum();
        billService.commitTransfer(decreasedBillID, increasedBillID, decreasedSum, increasedSum);
    }
}

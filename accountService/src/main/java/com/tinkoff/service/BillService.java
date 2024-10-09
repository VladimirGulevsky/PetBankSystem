package com.tinkoff.service;

import com.tinkoff.dao.BillRepository;
import com.tinkoff.entity.Bill;
import com.tinkoff.exceptions.NotFoundBillException;
import com.tinkoff.exceptions.NotEnoughMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {


    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill createBill(Long customerID, BigDecimal sum) {
        Bill bill = new Bill();
        bill.setCustomerID(customerID);
        bill.setSum(sum);
        billRepository.save(bill);
        return bill;
    }

    public Bill getBill(Long id) throws NotFoundBillException {

        Bill bill = billRepository.findById(id).orElse(null);
        if (bill == null) {
            throw new NotFoundBillException("Can't find bill with ID: " + id);
        } else {return bill;}
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Bill commitAdjustment(Long id, BigDecimal adjustment) {
        Bill bill = billRepository.findById(id).orElse(null);
        bill.setSum(bill.getSum().add(adjustment));
        billRepository.save(bill);
        return bill;
    }

    @Transactional(rollbackFor = Exception.class)
    public Bill commitPayment(Long id, BigDecimal payment) throws NotEnoughMoneyException {
        Bill bill = billRepository.findById(id).orElse(null);
        if (bill.getSum().compareTo(payment) < 0) {
            throw new NotEnoughMoneyException("Not enough money for payment on bill with ID: " + id);
        } else {
            bill.setSum(bill.getSum().subtract(payment));
        }
        billRepository.save(bill);
        return bill;
    }

    public List<Bill> getByCustomerID(Long customerID) {

        ArrayList<Bill> billList = (ArrayList<Bill>) billRepository.findByCustomerID(customerID);
        return billList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void commitTransfer(Long decreasedBillID, Long increasedBillID,
                               BigDecimal decreasedSum, BigDecimal increasedSum) throws NotFoundBillException {
        Bill decreasedBill = getBill(decreasedBillID);
        Bill increasedBill = getBill(increasedBillID);
        decreasedBill.setSum(decreasedSum);
        increasedBill.setSum(increasedSum);
        billRepository.save(decreasedBill);
        billRepository.save(increasedBill);
    }
}

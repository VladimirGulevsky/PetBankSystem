package com.tinkoff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tinkoff.controller.dto.BillDTO;
import com.tinkoff.controller.dto.BillTransferDTO;
import com.tinkoff.controller.dto.CustomerDTO;
import com.tinkoff.exceptions.NotFoundBillException;
import com.tinkoff.exceptions.NotEnoughMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CommonService {

    private static final String CUSTOMER_URI = "http://localhost:8082/customer/%s";
    private static final String BILL_URI = "http://localhost:8081/bill/get-by-customer-id/%s";
    private static final String TRANSFER_URI = "http://localhost:8081/bill/transfer";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    public CommonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CustomerDTO getCustomerInfo(Long customerID) {
        String resultCustomer = restTemplate.getForObject(String.format(CUSTOMER_URI, customerID), String.class);
        String resultBill = restTemplate.getForObject(String.format(BILL_URI, customerID), String.class);
        CustomerDTO customer = null;
        try {
            customer = objectMapper.readValue(resultCustomer, CustomerDTO.class);
            List<BillDTO> billList = objectMapper.readValue(resultBill, TypeFactory.defaultInstance().constructCollectionType(List.class, BillDTO.class));
            customer.setBillList(billList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void commitTransferForOneCustomer(Long customerID, Long decreasedBillID, Long increasedBillID, BigDecimal transfer) throws NotFoundBillException, NotEnoughMoneyException {
        try {
            List<BillDTO> list = getListOfBillsByCustomerID(customerID);
            BillDTO decreasedBill = getRequiredBill(decreasedBillID, list);
            BillDTO increasedBill = getRequiredBill(increasedBillID, list);
            checkBillForAbsence(decreasedBill, increasedBill, decreasedBillID, increasedBillID);
            commitTransfer(decreasedBill, increasedBill, transfer);
            sendChangedInfoToDatabase(decreasedBill, increasedBill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commitTransferForTwoCustomers(Long firstCustomerID, Long decreasedBillID, Long secondCustomerID, Long increasedBillID, BigDecimal transfer) throws NotFoundBillException, NotEnoughMoneyException {
        try {
            List<BillDTO> firstCustomerBillsList = getListOfBillsByCustomerID(firstCustomerID);
            BillDTO decreasedBill = getRequiredBill(decreasedBillID, firstCustomerBillsList);
            List<BillDTO> secondCustomerBillsList = getListOfBillsByCustomerID(secondCustomerID);
            BillDTO increasedBill = getRequiredBill(increasedBillID, secondCustomerBillsList);
            checkBillForAbsence(decreasedBill, increasedBill, decreasedBillID, increasedBillID);
            commitTransfer(decreasedBill, increasedBill, transfer);
            sendChangedInfoToDatabase(decreasedBill, increasedBill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BillDTO getRequiredBill(Long requiredBillID, List<BillDTO> billList) {
        BillDTO requiredBill = null;
        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getId().equals(requiredBillID)) {
                requiredBill = billList.get(i);
            }
        }
        return requiredBill;
    }

    private void checkBillForAbsence(BillDTO decreasedBill, BillDTO increasedBill, Long decreasedBillID, Long increasedBillID) throws NotFoundBillException {
        if (decreasedBill == null) {
            throw new NotFoundBillException("Can't find bill for cash decreasing with ID: " + decreasedBillID);
        }
        if (increasedBill == null) {
            throw new NotFoundBillException("Can't find bill for cash increasing with ID: " + increasedBillID);
        }
    }

    private void commitTransfer(BillDTO decreasedBill, BillDTO increasedBill, BigDecimal transfer) throws NotEnoughMoneyException {
        if (decreasedBill.getSum().compareTo(transfer) < 0) {
            throw new NotEnoughMoneyException("Not enough money for transaction on bill with ID: " + decreasedBill.getId());
        } else {
            decreasedBill.setSum(decreasedBill.getSum().subtract(transfer));
            increasedBill.setSum(increasedBill.getSum().add(transfer));
        }
    }

    private void sendChangedInfoToDatabase(BillDTO decreasedBill, BillDTO increasedBill) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            String requestJson = objectMapper.writeValueAsString(new BillTransferDTO(decreasedBill, increasedBill));
            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(TRANSFER_URI, entity, String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<BillDTO> getListOfBillsByCustomerID(Long customerID) throws IOException {
        String resultBill = restTemplate.getForObject(String.format(BILL_URI, customerID), String.class);
        return objectMapper.readValue(resultBill, TypeFactory.defaultInstance().constructCollectionType(List.class, BillDTO.class));
    }
}

package com.tinkoff.controller;

import com.tinkoff.controller.dto.CustomerDTO;
import com.tinkoff.entity.Customer;
import com.tinkoff.exceptions.NotFoundCustomerException;
import com.tinkoff.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO.getName(), customerDTO.getSurname());
    }


    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable @NotNull (message = "Should be initialized") Long id) {
        customerService.deleteCustomer(id);
    }

    @RequestMapping(value = "/customer/change/{id}", method = RequestMethod.PUT)
    public Customer changeCustomer(@PathVariable @NotNull (message = "Should be initialized") Long id,
                                   @RequestBody @Valid CustomerDTO customerDTO) {
        return customerService.changeCustomer(id, customerDTO.getName(), customerDTO.getSurname());
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public Customer getById(@PathVariable @NotNull (message = "Should be initialized") Long id) throws NotFoundCustomerException {
        return customerService.getCustomer(id);
    }

    @RequestMapping(value = "/customer/all", method = RequestMethod.GET)
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }
}

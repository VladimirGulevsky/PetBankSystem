package com.tinkoff.service;

import com.tinkoff.dao.CustomerRepository;
import com.tinkoff.entity.Customer;
import com.tinkoff.exceptions.NotFoundCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, String surname) {

        Customer account = new Customer();
        account.setName(name);
        account.setSurname(surname);
        customerRepository.save(account);
        return account;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer changeCustomer(Long id, String name, String surname) {

        Customer account = customerRepository.findById(id).orElse(null);
        account.setName(name);
        account.setSurname(surname);
        customerRepository.save(account);
        return account;
    }

    public Customer getCustomer(Long id) throws NotFoundCustomerException {

        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new NotFoundCustomerException("Can't find customer with ID: " + id);
        } else return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

package com.tinkoff.dao;

import com.tinkoff.entity.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {

    List<Bill> findByCustomerID(Long customerID);

}

package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(Integer id);
}

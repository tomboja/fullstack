package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer selectCustomerById(Integer id);

    Customer saveCustomer(Customer customer);

    boolean existsPersonWithEmail(String email);

    boolean deleteCustomerById(Integer id);

    Customer updateCustomer(CustomerRegistrationRequest request, Integer id);
}

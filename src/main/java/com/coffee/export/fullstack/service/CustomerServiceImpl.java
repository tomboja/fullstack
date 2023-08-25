package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.exception.ResourceNotFound;
import com.coffee.export.fullstack.repository.CustomerDAO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerDAO
                .findById(id)
                // .orElseThrow(() -> new IllegalArgumentException("Customer with [%s] not found".formatted(id)));
                .orElseThrow(() -> new ResourceNotFound("Customer with [%s] not found".formatted(id)));
    }
}

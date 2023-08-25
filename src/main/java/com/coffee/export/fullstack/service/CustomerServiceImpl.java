package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.exception.ResourceNotFound;
import com.coffee.export.fullstack.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerDao;

    public CustomerServiceImpl(CustomerRepository customerDAO) {
        this.customerDao = customerDAO;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerDao.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerDao
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Customer with [%s] is not found".formatted(id)));
    }
}

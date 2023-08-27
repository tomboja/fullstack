package com.coffee.export.fullstack.controller;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;
import com.coffee.export.fullstack.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/24/23
 */

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(@Qualifier("jpaBasedService") CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.selectCustomerById(customerId);
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    public boolean deleteCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.deleteCustomerById(customerId);
    }

    @PutMapping("/{customerId}")
    public Customer updateCustomer(@RequestBody CustomerRegistrationRequest request,
                                   @PathVariable("customerId") Integer customerId) {
        return customerService.updateCustomer(request, customerId);
    }
}

package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;
import com.coffee.export.fullstack.exception.DuplicateResourceException;
import com.coffee.export.fullstack.exception.RequestValidationException;
import com.coffee.export.fullstack.exception.ResourceNotFoundException;
import com.coffee.export.fullstack.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

@Service("jpaBasedService")
public class CustomerServiceJpaImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceJpaImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @Override
    public Customer selectCustomerById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with id [%s] does not exist.", id)));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        String email = customer.getEmail();
        checkCustomerEmailIsUnique(email);
        return repository.save(customer);
    }

    public void checkCustomerEmailIsUnique(String email) {
        boolean emailExists = repository.existsCustomerByEmail(email);
        if (emailExists) {
            throw new DuplicateResourceException("Email already exists");
        }
    }

    @Override
    public boolean deleteCustomerById(Integer id) {
        boolean customerExists = repository.existsById(id);
        if (!customerExists) {
            throw new ResourceNotFoundException(String.format("Customer with id [%s] does not exist.", id));
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return repository.existsCustomerByEmail(email);
    }

    @Override
    public Customer updateCustomer(CustomerRegistrationRequest request, Integer id) {
        Customer customer = selectCustomerById(id);

        boolean customerIdChanged = false;

        // Check customer name is changes
        if (request.name() != null && !request.name().equals(customer.getName())) {
            customer.setName(request.name());
            customerIdChanged = true;
        }

        // Check customer email is changes
        if (request.email() != null && !request.email().equals(customer.getEmail())) {
            checkCustomerEmailIsUnique(request.email());
            customer.setEmail(request.email());
            customerIdChanged = true;
        }

        // Check customer age is changes
        if (request.age() != null && !request.age().equals(customer.getAge())) {
            customer.setAge(request.age());
            customerIdChanged = true;
        }

        if(!customerIdChanged) {
            throw new RequestValidationException("Customer data has not changed");
        }

        return repository.save(customer);
    }
}

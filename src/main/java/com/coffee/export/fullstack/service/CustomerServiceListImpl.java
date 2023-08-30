package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;
import com.coffee.export.fullstack.exception.DuplicateResourceException;
import com.coffee.export.fullstack.exception.RequestValidationException;
import com.coffee.export.fullstack.exception.ResourceNotFoundException;
import com.coffee.export.fullstack.repository.CustomerRepositoryList;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/26/23
 */

@Service("listBaseService")
public class CustomerServiceListImpl implements CustomerService {
    private final CustomerRepositoryList repositoryList;

    public CustomerServiceListImpl(CustomerRepositoryList repositoryList) {
        this.repositoryList = repositoryList;
    }

    @Override
    public boolean deleteCustomerById(Integer id) {
        return repositoryList.deleteCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repositoryList.getCustomers();
    }

    @Override
    public Customer selectCustomerById(Integer id) {
        return repositoryList.getCustomers().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with id [%s] doesn't exist.", id)));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        String email = customer.getEmail();
        checkCustomerEmailIsUnique(email);

        Customer newCustomer = new Customer();
        newCustomer.setId(repositoryList.getCustomers().size() + 1);
        newCustomer.setName(customer.getName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setAge(customer.getAge());

        repositoryList.getCustomers().add(newCustomer);
        return newCustomer;
    }

    private void checkCustomerEmailIsUnique(String email) {
        boolean customerExists = existsPersonWithEmail(email);
        if (customerExists) {
            throw new DuplicateResourceException(String.format("Customer with email [%s] already exists", email));
        }
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return repositoryList.existsCustomerWithEmail(email);
    }

    @Override
    public Customer updateCustomer(CustomerRegistrationRequest request, Integer id) {
        Customer customer = selectCustomerById(id);
        boolean isChanged = false;

        if (request.name() != null && !request.name().equals(customer.getName())) {
            customer.setName(request.name());
            isChanged = true;
        }
        if (request.email() != null && !request.email().equals(customer.getEmail())) {
            checkCustomerEmailIsUnique(request.email());
            customer.setEmail(request.email());
            isChanged = true;
        }
        if (request.age() != null && !request.age().equals(customer.getAge())) {
            customer.setAge(request.age());
            isChanged = true;
        }

        if (!isChanged) {
            throw new RequestValidationException("Customer data has not changed");
        }

        repositoryList.getCustomers().add(customer);

        return customer;
    }
}

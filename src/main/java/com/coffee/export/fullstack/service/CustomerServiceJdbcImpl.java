package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;
import com.coffee.export.fullstack.exception.DuplicateResourceException;
import com.coffee.export.fullstack.exception.RequestValidationException;
import com.coffee.export.fullstack.exception.ResourceNotFoundException;
import com.coffee.export.fullstack.repository.CustomerRepositoryJdbc;
import com.coffee.export.fullstack.utility.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/28/23
 */

@Service("jdbcBasedService")
public class CustomerServiceJdbcImpl implements CustomerService {

    private final CustomerRepositoryJdbc jdbcRepository;
    private final CustomerRowMapper customerRowMapper;
    private final JdbcTemplate jdbcTemplate;

    public CustomerServiceJdbcImpl(CustomerRepositoryJdbc jdbcRepository,
                                   CustomerRowMapper customerRowMapper,
                                   JdbcTemplate jdbcTemplate) {
        this.jdbcRepository = jdbcRepository;
        this.customerRowMapper = customerRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """
                SELECT customerid, name, email, age
                FROM customer
                """;

        return jdbcRepository.findAllCustomers(sql, customerRowMapper);
    }

    @Override
    public Customer selectCustomerById(Integer id) {
        String sql = """
                SELECT customerid, name, email, age
                FROM customer
                WHERE customerid = ?
                """;
        return jdbcRepository.findById(sql, customerRowMapper, id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?,?,?)
                """;
        boolean customerWithEmailExists = existsPersonWithEmail(customer.getEmail());
        if (customerWithEmailExists) {
            throw new DuplicateResourceException("Email already exists.");
        }
        return jdbcRepository.saveCustomer(customer, sql);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT * FROM customer
                WHERE email = ?
                """;
        int result = jdbcRepository.findByEmail(sql, customerRowMapper, email);
        return result != 0;
    }

    public boolean existsCustomerWithId(Integer customerId) {
        var sql = """
                SELECT * FROM customer WHERE customerid = ?
                """;
        Customer result = jdbcRepository.findById(sql, customerRowMapper, customerId);
        System.out.println("Result>> " + result);
        return true;

    }

    @Override
    public boolean deleteCustomerById(Integer id) {
        var sql = """
                DELETE FROM customer WHERE customerid = ?
                """;
        Customer customer = selectCustomerById(id);
        if (customer.getId() == null) {
            throw new ResourceNotFoundException(String.format("Customer with id [%s] does not exist.", id));
        }
        int result = jdbcRepository.deleteCustomer(sql, id);
        return (result == 1);
    }

    //    @Override
    public Customer updateCustomer(CustomerRegistrationRequest request, Integer id) {
        Customer savedCustomer = selectCustomerById(id);
        boolean customerIdHasChanged = false;

        // Update name if name has changed
        if (request.name() != null
                && !savedCustomer.getName().equals(request.name())) {
            String sql = """
                    UPDATE customer SET name = ? WHERE customerid = ?
                    """;
            int result = jdbcTemplate.update(sql, request.name(), id);
            savedCustomer.setName(request.name());
            customerIdHasChanged = true;
        }

        // Update email if email has changes
        if (request.email() != null && !savedCustomer.getEmail().equals(request.email())) {
            String sql = """
                    UPDATE customer SET email = ? WHERE customerid = ?
                    """;
            int result = jdbcTemplate.update(sql, request.email(), id);
            savedCustomer.setEmail(request.email());
            customerIdHasChanged = true;
        }

        // Update age if age has changed
        if (request.age() != null && savedCustomer.getAge() != request.age()) {
            String sql = """
                    UPDATE customer SET age = ? WHERE customerid = ?
                    """;
            int result = jdbcTemplate.update(sql, request.age(), id);
            savedCustomer.setAge(request.age());
            customerIdHasChanged = true;
        }

        if (!customerIdHasChanged) {
            throw new RequestValidationException("Customer data has not changed");
        }

        return savedCustomer;
    }
}

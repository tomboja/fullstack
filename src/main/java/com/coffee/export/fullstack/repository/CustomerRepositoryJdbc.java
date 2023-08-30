package com.coffee.export.fullstack.repository;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.exception.ResourceNotFoundException;
import com.coffee.export.fullstack.utility.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/28/23
 */

@Repository
public class CustomerRepositoryJdbc implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer saveCustomer(Customer customer, String sqlQuery) {
        int result = jdbcTemplate
                .update(sqlQuery, customer.getName(), customer.getEmail(), customer.getAge());

        return customer;
    }

    public List<Customer> findAllCustomers(String sqlQuery, RowMapper<Customer> rowMapper) {
        return jdbcTemplate.query(sqlQuery, rowMapper);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return false;
    }

    public Customer findById(String sql, CustomerRowMapper customerRowMapper, Integer id) {
        return jdbcTemplate
                .query(sql, customerRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with id [%s] does not exist.", id)));
    }

    public int findByEmail(String sql, CustomerRowMapper customerRowMapper, String email) {
        return jdbcTemplate
                .query(sql, customerRowMapper, email)
                .stream()
                .toList()
                .size();
    }

    public int deleteCustomer(String sql, Integer id) {
        return jdbcTemplate.update(sql, id);
    }
}

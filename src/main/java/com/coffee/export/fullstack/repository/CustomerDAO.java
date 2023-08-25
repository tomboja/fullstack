package com.coffee.export.fullstack.repository;

import com.coffee.export.fullstack.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

@Repository
//public abstract class CustomerDAO implements JpaRepository<Customer, Integer> {
public class CustomerDAO {
    private final List<Customer> customerList = new ArrayList<>();

    public CustomerDAO() {
        Customer customer1 = new Customer(20, "James", "james@email.com", 28);
        Customer customer2 = new Customer(2, "Jason", "jason@email.com", 30);

        customerList.add(customer2);
        customerList.add(customer1);
    }

    public List<Customer> findAll() {
        return customerList;
    }

    public Optional<Customer> findById(Integer id) {
        return customerList.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }
}

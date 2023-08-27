package com.coffee.export.fullstack.repository;

import com.coffee.export.fullstack.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/26/23
 */

@Repository
public class CustomerRepositoryList implements CustomerDao {
    List<Customer> customers = new ArrayList<>();

    Customer customer1 = new Customer(1, "James", "james@email.com", 28);
    Customer customer2 = new Customer(2, "Jason", "jason@email.com", 30);

    public CustomerRepositoryList() {
        customers.add(customer1);
        customers.add(customer2);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean deleteCustomerById(Integer id) {
        return customers.removeIf(customer -> customer.getId().equals(id));
//        customers.stream()
//                .filter(customer -> customer.getId().equals(id))
//                .findFirst()
//                .ifPresent(customers::remove);
//        return true;
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers
                .stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }
}

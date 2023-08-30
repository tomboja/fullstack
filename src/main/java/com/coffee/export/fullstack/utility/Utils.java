package com.coffee.export.fullstack.utility;

import com.coffee.export.fullstack.domain.Customer;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/27/23
 */

public class Utils {
    public static List<Customer> generateFakeCustomers() {
        List<Customer> customers = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
            Integer age = faker.number().numberBetween(18, 40);
            Customer customer = new Customer(lastName + ", " + firstName, email, age);
            customers.add(customer);
        }
        return customers;
    }
}

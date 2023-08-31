package com.coffee.export.fullstack.utility;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/27/23
 */

public class Utils {
    public static List<CustomerRegistrationRequest> generateFakeCustomers(int numberOfCustomers) {
        List<CustomerRegistrationRequest> customers = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < numberOfCustomers; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
            Integer age = faker.number().numberBetween(18, 40);
            CustomerRegistrationRequest customer = new CustomerRegistrationRequest(lastName + ", " + firstName, email, age);
            customers.add(customer);
        }
        return customers;
    }
}

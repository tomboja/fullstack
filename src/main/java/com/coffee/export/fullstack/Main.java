package com.coffee.export.fullstack;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        // See what beans are managed by Spring in the Application Context
        String[] beans = context.getBeanDefinitionNames();
        for (String bean : beans) {
            // System.out.println(bean);
        }
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer("James", "james@email.com", 28);
        Customer customer2 = new Customer("Jason", "jason@email.com", 30);
        customers.add(customer2);
        customers.add(customer1);


        return args -> {
            customerRepository.saveAll(customers);
        };
    }

}

package com.coffee.export.fullstack;

import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coffee.export.fullstack.utility.Utils.generateFakeCustomers;


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
        List<Customer> customers = generateFakeCustomers();

        return args -> {
            // customerRepository.saveAll(customers);
        };
    }

}

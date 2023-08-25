package com.coffee.export.fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;


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

}

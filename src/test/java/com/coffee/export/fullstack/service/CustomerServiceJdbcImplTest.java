package com.coffee.export.fullstack.service;

import com.coffee.export.fullstack.AbstractDaoBaseTestClass;
import com.coffee.export.fullstack.domain.Customer;
import com.coffee.export.fullstack.domain.dto.CustomerRegistrationRequest;
import com.coffee.export.fullstack.exception.DuplicateResourceException;
import com.coffee.export.fullstack.exception.RequestValidationException;
import com.coffee.export.fullstack.exception.ResourceNotFoundException;
import com.coffee.export.fullstack.repository.CustomerRepositoryJdbc;
import com.coffee.export.fullstack.utility.CustomerRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/31/23
 */

class CustomerServiceJdbcImplTest extends AbstractDaoBaseTestClass {

    private final CustomerRowMapper rowMapper = new CustomerRowMapper();
    private final JdbcTemplate jdbcTemplate = getJdbcTemplate();
    private final CustomerRepositoryJdbc repositoryJdbc = new CustomerRepositoryJdbc(jdbcTemplate);
    private CustomerServiceJdbcImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerServiceJdbcImpl(repositoryJdbc, rowMapper, jdbcTemplate);
    }

    @Test
    void getAllCustomers() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                UUID.randomUUID() + "_" + FAKER.internet().emailAddress(), // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        underTest.saveCustomer(customer);

        // When
        List<Customer> customers = underTest.getAllCustomers();

        // Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                UUID.randomUUID() + "_" + FAKER.internet().emailAddress(), // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        underTest.saveCustomer(customer);

        int savedCustomerId = underTest
                .getAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        // When
        Customer actualCustomer = underTest.selectCustomerById(savedCustomerId);

        // Then
        assertThat(actualCustomer.getId()).isEqualTo(savedCustomerId);
        assertThat(actualCustomer.getName()).isEqualTo(customer.getName());
        assertThat(actualCustomer.getEmail()).isEqualTo(customer.getEmail());

    }

    @Test
    void failsWithCustomerWithNonAvailableId() {
        // Given
        int id = -1; // Have id as value that is not in database

        // When

        // Then
        assertThatThrownBy(() -> {
            underTest.selectCustomerById(id);
        }).isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    void saveCustomer() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                UUID.randomUUID() + "_" + FAKER.internet().emailAddress(), // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        List<Customer> customersListBeforeInsert = underTest.getAllCustomers();

        // When
        Customer savedCustomer = underTest.saveCustomer(customer);
        List<Customer> customersListAfterInsert = underTest.getAllCustomers();

        // Then
        assertThat(customersListAfterInsert).isNotEmpty();
        assertThat(customersListAfterInsert.size()).isGreaterThan(customersListBeforeInsert.size());

        assertThat(savedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(savedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void saveDuplicateCustomer() {
        // Given
        String email = UUID.randomUUID() + "_" + FAKER.internet().emailAddress();
        Customer customer1 = new Customer(
                FAKER.name().fullName(),
                email, // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        Customer customer2 = new Customer(
                FAKER.name().fullName(),
                email, // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );

        // When
        // Add customer with duplicate email
        underTest.saveCustomer(customer1);

        // Then, throws Duplicate email exception since email must be unique
        assertThatThrownBy(() -> {
            underTest.saveCustomer(customer2);
        }).isInstanceOf(DuplicateResourceException.class);

    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = UUID.randomUUID() + "_" + FAKER.internet().emailAddress();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email, // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        underTest.saveCustomer(customer);

        // When
        boolean exists = underTest.existsPersonWithEmail(email);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsCustomerWithId() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                UUID.randomUUID() + "_" + FAKER.internet().emailAddress(), // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        underTest.saveCustomer(customer);

        int savedCustomerId = underTest
                .getAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        // When
        boolean existsById = underTest.existsCustomerWithId(savedCustomerId);

        // Then
        assertThat(existsById).isTrue();
    }

    @Test
    void deleteCustomerById() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                UUID.randomUUID() + "_" + FAKER.internet().emailAddress(), // To make sure that it is absolutely random
                FAKER.number().numberBetween(20, 30)
        );
        underTest.saveCustomer(customer);

        int savedCustomerId = underTest
                .getAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        // When
        boolean deleted = underTest.deleteCustomerById(savedCustomerId);

        // Then
        assertThat(deleted).isTrue();
    }

    @Test
    void deleteCustomerThatDoesNotExist() {
        // Given
        String email = UUID.randomUUID() + "_" + FAKER.internet().emailAddress();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(20, 40)
        );
        underTest.saveCustomer(customer);

        int savedCustomerId = underTest.getAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        // Delete customer with savedCustomerId
        underTest.deleteCustomerById(savedCustomerId);

        // Then, re-deleting the same customer will throw Resource not found exception
        assertThatThrownBy(() -> {
            underTest.deleteCustomerById(savedCustomerId);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateCustomerWithNameChanged() {
        // Given
        Customer customer = new Customer(
                "Jason Bourne",
                FAKER.internet().emailAddress(),
                FAKER.number().numberBetween(20, 40)
        );

        underTest.saveCustomer(customer);

        int savedCustomerId = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();


        // When
        String newName = "James, Brown";
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(newName, customer.getEmail(), customer.getAge());
        Customer updatedCustomer = underTest.updateCustomer(request, savedCustomerId);

        // Then
        assertThat(updatedCustomer.getName()).isEqualTo(newName);
        assertThat(updatedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(updatedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void updateCustomerWithEmailChanged() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                "sampleEmail@email.com",
                FAKER.number().numberBetween(20, 40)
        );

        underTest.saveCustomer(customer);

        int savedCustomerId = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();


        // When
        String newEmail = "anotherEmail@sample.com";
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer.getName(), newEmail, customer.getAge());
        Customer updatedCustomer = underTest.updateCustomer(request, savedCustomerId);

        // Then
        assertThat(updatedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);
        assertThat(updatedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void updateCustomerWithAgeChanged() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress(),
                23
        );

        underTest.saveCustomer(customer);

        int savedCustomerId = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();


        // When
        int newAge = 44;
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer.getName(), customer.getEmail(), newAge);
        Customer updatedCustomer = underTest.updateCustomer(request, savedCustomerId);

        // Then
        assertThat(updatedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(updatedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(updatedCustomer.getAge()).isEqualTo(newAge);
    }

    @Test
    void updateCustomerWithNothingChanged() {
        // Given
        Customer customer = new Customer(
                "Jason Bourne",
                "sample@email.com",
                23
        );

        underTest.saveCustomer(customer);

        int savedCustomerId = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        // When
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer.getName(), customer.getEmail(), customer.getAge());

        // Then
        assertThatThrownBy(() -> {
            underTest.updateCustomer(request, savedCustomerId);
        }).isInstanceOf(RequestValidationException.class);
    }
}
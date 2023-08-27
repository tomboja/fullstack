package com.coffee.export.fullstack.repository;

import com.coffee.export.fullstack.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/25/23
 */

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsCustomerByEmail(String email);
}

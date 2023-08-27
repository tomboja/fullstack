package com.coffee.export.fullstack.repository;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/26/23
 */

public interface CustomerDao {
    boolean existsCustomerWithEmail(String email);
}

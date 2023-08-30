package com.coffee.export.fullstack.utility;

import com.coffee.export.fullstack.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/28/23
 */

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getInt("customerid"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        );
    }
}

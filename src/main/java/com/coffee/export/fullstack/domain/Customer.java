package com.coffee.export.fullstack.domain;

import lombok.*;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/24/23
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode
public class Customer {
    private Integer id;
    private String name;
    private String email;
    private int age;
}

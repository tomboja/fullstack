package com.coffee.export.fullstack.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigInteger;
import java.util.Objects;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/24/23
 */

//@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "customer_email_unique", columnNames = {"email"})
        }
)
public class Customer {
    @Id
    @SequenceGenerator(name = "customer_customerid_seq", sequenceName = "customer_customerid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customer_customerid_seq")
    @Column(name = "customerid", columnDefinition = "BIGINTEGER")
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int age;


    public Customer(String name, String mail, int age) {
        this.age = age;
        this.email = mail;
        this.name = name;
    }

    public Customer(Integer id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

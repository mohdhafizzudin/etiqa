package com.etiqa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "customer", schema = "public")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "public.customer_id_seq", allocationSize = 1)
    Long customerId;
    String firstName;
    String lastName;
    String emailOffice;
    String emailPersonal;

    @Builder
    public CustomerEntity(Long customerId, String firstName, String lastName, String emailOffice, String emailPersonal) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailOffice = emailOffice;
        this.emailPersonal = emailPersonal;
    }
}

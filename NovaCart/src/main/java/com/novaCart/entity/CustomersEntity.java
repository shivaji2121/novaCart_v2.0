package com.novaCart.entity;

import com.novaCart.utils.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "customers")
public class CustomersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;


    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",unique = true,nullable = false)
    private  String email;

    @Column(name = "password",nullable = false)
    private  String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active")
    private CustomerStatus customerStatus;

    @Column(name = "age")
    private  Integer age;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private  LocalDateTime deletedAt;

    //one user may have multiple addresses
    //orphan removal means--when u remove child relation from parent then the related child is automatically deleted
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> address;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrdersEntity> orders;

}

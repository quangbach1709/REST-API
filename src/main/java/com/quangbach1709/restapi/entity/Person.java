package com.quangbach1709.restapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.swing.*;
import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "full_name")
    private String fullName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false,name = "birthdate")
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(nullable = false,name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @OneToOne(mappedBy = "person",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private User user;

}

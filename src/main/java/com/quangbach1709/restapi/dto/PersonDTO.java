package com.quangbach1709.restapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor

public class PersonDTO {
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate birthdate;
    private String phoneNumber;
    private String address;

    public PersonDTO(Long id, String fullName, String gender, LocalDate birthdate, String phoneNumber, String address) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}

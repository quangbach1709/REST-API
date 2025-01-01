package com.quangbach1709.restapi.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class PersonDTO {
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate birthdate;
    private String phoneNumber;
    private String address;
}

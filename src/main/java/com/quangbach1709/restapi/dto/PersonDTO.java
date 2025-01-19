package com.quangbach1709.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PersonDTO {
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate birthdate;
    private String phoneNumber;
    private String address;
    private Long companyId;
}

package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Person;

public class PersonMapper {

    public static PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFullName(person.getFullName());
        dto.setGender(person.getGender());
        dto.setBirthdate(person.getBirthDate());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setAddress(person.getAddress());
        // Điền companyId nếu có
        if (person.getCompany() != null) {
            dto.setCompanyId(person.getCompany().getId());
        }
        return dto;
    }

    public static Person toEntity(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setFullName(dto.getFullName());
        person.setGender(dto.getGender());
        person.setBirthDate(dto.getBirthdate());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        return person;
    }
}

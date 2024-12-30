package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Country;

public class CountryMapper {

    public static CountryDTO toDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setName(country.getName());
        countryDTO.setCode(country.getCode());
        countryDTO.setDescription(country.getDescription());
        return countryDTO;
    }

    public static Country toEntity(CountryDTO countryDTO) {
        Country country = new Country();
        country.setId(countryDTO.getId());
        country.setName(countryDTO.getName());
        country.setCode(countryDTO.getCode());
        country.setDescription(countryDTO.getDescription());
        return country;
    }


}
